package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;

/**
 * Corresponds to the javax.persistence.MapKey annotation
 */
public interface MapKey extends JavaResource
{
	String getName();
	
	void setName(String name);
	
	/**
	 * Return the ITextRange for the name element.  If the name element 
	 * does not exist return the ITextRange for the MapKey annotation.
	 */
	ITextRange nameTextRange(CompilationUnit astRoot);

	/**
	 * Return whether the specified postition touches the name element.
	 * Return false if the name element does not exist.
	 */
	public boolean nameTouches(int pos, CompilationUnit astRoot);
}
