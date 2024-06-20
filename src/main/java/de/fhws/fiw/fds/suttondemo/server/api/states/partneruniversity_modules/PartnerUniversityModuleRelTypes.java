package de.fhws.fiw.fds.suttondemo.server.api.states.partneruniversity_modules;

public interface PartnerUniversityModuleRelTypes {
	String CREATE_MODULE = "createModuleOfPartnerUniversity";
	String GET_ALL_LINKED_MODULES = "getAllModulesOfPartnerUniversity";
	String GET_ALL_MODULES = "getAllLinkableModules";
	String UPDATE_SINGLE_MODULE = "updateModuleOfPartnerUniversity";
	String CREATE_LINK_FROM_PARTNERUNIVERSITY_TO_MODULE = "linkPartnerUniversityToModule";
	String DELETE_LINK_FROM_PARTNERUNIVERSITY_TO_MODULE = "unlinkPartnerUniversityToModule";
	String GET_SINGLE_MODULE = "getModuleOfPartnerUniversity";

}
