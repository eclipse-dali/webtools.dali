package org.eclipse.jpt.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.TextRange;

/**
 * Corresponds to the javax.persistence.MapKey annotation
 */
public interface MapKey extends JavaResourceNode
{
	String ANNOTATION_NAME = JPA.MAP_KEY;

	String getName();	
	void setName(String name);
		String NAME_PROPERTY = "nameProperty";
		
	/**
	 * Return the ITextRange for the name element.  If the name element 
	 * does not exist return the ITextRange for the MapKey annotation.
	 */
	TextRange nameTextRange(CompilationUnit astRoot);

	/**
	 * Return whether the specified postition touches the name element.
	 * Return false if the name element does not exist.
	 */
	public boolean nameTouches(int pos, CompilationUnit astRoot);
}
