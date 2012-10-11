package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.utility.internal.iterable.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterable.CompositeIterable;
import org.eclipse.jpt.jaxb.core.internal.context.java.JavaXmlAnyElementMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;


public class ELJavaXmlAnyElementMappingDefinition
		extends JavaXmlAnyElementMappingDefinition {
	
	// singleton
	private static final ELJavaXmlAnyElementMappingDefinition INSTANCE = 
			new ELJavaXmlAnyElementMappingDefinition();
	
	
	private static final String[] SUPPORTING_ANNOTATION_NAMES = 
			{
				ELJaxb.XML_PATH };
	
	/**
	 * Return the singleton.
	 */
	public static ELJavaXmlAnyElementMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	protected ELJavaXmlAnyElementMappingDefinition() {
		super();
	}
	
	
	@Override
	public Iterable<String> getSupportingAnnotationNames() {
		return new CompositeIterable<String>(
				super.getSupportingAnnotationNames(),
				new ArrayIterable<String>(SUPPORTING_ANNOTATION_NAMES));
	}
}
