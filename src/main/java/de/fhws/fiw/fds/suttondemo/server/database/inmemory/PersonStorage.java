package de.fhws.fiw.fds.suttondemo.server.database.inmemory;

import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryStorage;
import de.fhws.fiw.fds.sutton.server.database.inmemory.InMemoryPaging;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.suttondemo.server.api.models.Person;
import de.fhws.fiw.fds.suttondemo.server.database.PersonDao;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PersonStorage extends AbstractInMemoryStorage<Person> implements PersonDao {
    
    @Override
    public CollectionModelResult<Person> readByFirstNameAndLastName(String firstName, String lastName, SearchParameter searchParameter) {
        // Filter by first and last name
        CollectionModelResult<Person> filteredResult = new CollectionModelResult<>(
            filterBy(byFirstAndLastName(firstName, lastName))
        );
    
        // Sort the filtered result by orderByAttribute
        searchParameter.getOrderByAttribute();
        List<Person> sortedList = filteredResult.getResult().stream()
            .sorted((Person a, Person b) -> {
                return a.getFirstName().toLowerCase(Locale.getDefault())
                    .compareTo(b.getFirstName().toLowerCase(Locale.getDefault()));
            })
            .collect(Collectors.toList());
    
        // Paginate the sorted result
        CollectionModelResult<Person> pagedResult = InMemoryPaging.page(
            new CollectionModelResult<>(sortedList), 
            searchParameter.getOffset(), 
            searchParameter.getSize()
        );
    
        // Clone the paged result
        CollectionModelResult<Person> returnValue = new CollectionModelResult<>(
            clone(pagedResult.getResult())
        );
        returnValue.setTotalNumberOfResult(filteredResult.getTotalNumberOfResult());
    
        return returnValue;
    }
	private Collection<Person> filterBy(final Predicate<Person> predicate) {
		return this.storage.values().stream().filter(predicate).collect(Collectors.toList());
	}
    public void resetDatabase() {
        this.storage.clear();
    }

    private Predicate<Person> byFirstAndLastName(String firstName, String lastName) {
        return p -> (firstName.isEmpty() || p.getFirstName().equals(firstName) ) && ( lastName.isEmpty() || p.getLastName().equals(lastName));
    }

}
