package de.fhws.fiw.fds.suttondemo.server.database.inmemory;

import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryStorage;
import de.fhws.fiw.fds.sutton.server.database.inmemory.InMemoryPaging;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.suttondemo.server.api.models.PartnerUniversity;
import de.fhws.fiw.fds.suttondemo.server.database.PartnerUniversityDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class PartnerUniversityStorage extends AbstractInMemoryStorage<PartnerUniversity> implements PartnerUniversityDao {
    private HashMap<String, Comparator<PartnerUniversity>> orderParameterToOrder = new HashMap<String, Comparator<PartnerUniversity>>() {{
        put("name", (PartnerUniversity a, PartnerUniversity b) -> {
            return a.getName().toLowerCase(Locale.getDefault())
                    .compareTo(b.getName().toLowerCase(Locale.getDefault()));
        });
    }};
    
    @Override
    public CollectionModelResult<PartnerUniversity> readByName(String name, SearchParameter searchParameter) {
        // Filter by university name
        List<PartnerUniversity> filteredList = new ArrayList<>(
            filterBy(byUniversityName(name))
        );

        // Parse the order by attribute
        String order = searchParameter.getOrderByAttribute();
        System.out.println(order);
        boolean orderExists = order.length()>2;
        String orderBy = orderExists?order.substring(1):null;
        String ascDsc = orderExists?""+order.charAt(0):null;
        System.out.println(orderBy);
        System.out.println(ascDsc);

        // Validate the order attribute and order direction
        boolean isOrderAttributeValid = orderParameterToOrder.containsKey(orderBy) && (ascDsc.equals("+") || ascDsc.equals("-"));

        List<PartnerUniversity> sortedList;

        if (isOrderAttributeValid) {
            Comparator<PartnerUniversity> comparator = orderParameterToOrder.get(orderBy);
            if (ascDsc.equals("-")) {
                comparator = comparator.reversed();
            }
            sortedList = filteredList.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
        } else {
            // If the order attribute is invalid, return the filtered result unsorted
            sortedList = filteredList;
        }

        // Paginate the sorted result
        CollectionModelResult<PartnerUniversity> pagedResult = InMemoryPaging.page(
            new CollectionModelResult<>(sortedList),
            searchParameter.getOffset(),
            searchParameter.getSize()
        );

        // Clone the paged result
        CollectionModelResult<PartnerUniversity> returnValue = new CollectionModelResult<>(
            clone(pagedResult.getResult())
        );
        returnValue.setTotalNumberOfResult(filteredList.size());

        return returnValue;
    }
    
    private Collection<PartnerUniversity> filterBy(final Predicate<PartnerUniversity> predicate) {
		return this.storage.values().stream().filter(predicate).collect(Collectors.toList());
	}

    public void resetDatabase() {
        this.storage.clear();
    }

    private Predicate<PartnerUniversity> byUniversityName(String name) {
        return p -> (name.isEmpty() || p.getName().equals(name) );
    }

}
