package org.eclipse.jpt.jaxb.eclipselink.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the EclipseLink annotation
 * org.eclipse.persistence.oxm.annotations.XmlPath
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 3.2
 */
public interface XmlPathAnnotation
		extends NestableAnnotation {
	
	/**
	 * String associated with change events to the 'value' property
	 */
	String VALUE_PROPERTY = "value"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'value' element of the annotation.
	 * Return null if the element does not exist in Java (the annotation is a marker annotation.)
	 */
	String getValue();
	
	/**
	 * Corresponds to the 'value' element of the annotation.
	 * Set to null to remove the element (to make it a marker annotation.)
	 */
	void setValue(String value);
	
	/**
	 * Return the text range associated with the 'value' element.
	 * Return the text range of this annotation if the element is absent.
	 */
	TextRange getValueTextRange(CompilationUnit astRoot);
	
	/**
	 * Return whether the specified text position is within the 'value' element.
	 */
	boolean valueTouches(int pos, CompilationUnit astRoot);
}
