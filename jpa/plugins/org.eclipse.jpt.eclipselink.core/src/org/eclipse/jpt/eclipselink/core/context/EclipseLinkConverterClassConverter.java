package org.eclipse.jpt.eclipselink.core.context;

/**
 * Corresponds to a *ConverterClassConverter resource model object
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.4
 * @since 2.4
 */

public interface EclipseLinkConverterClassConverter extends
		EclipseLinkConverter {

	String getConverterClass();	
	void setConverterClass(String converterClass);
		String CONVERTER_CLASS_PROPERTY = "converterClass"; //$NON-NLS-1$
}
