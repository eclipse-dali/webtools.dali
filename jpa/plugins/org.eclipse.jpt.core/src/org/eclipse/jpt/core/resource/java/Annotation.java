/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import java.util.Map;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * Common Java resource annotation behavior
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.0
 */
public interface Annotation
	extends JavaResourceNode
{
	/**
	 * Return the annotation's fully qualified name, as opposed to the value of
	 * the annotation's <code>name</code> element. For example:<pre>
	 *     &#64;com.foo.Bar(name="Thomas")
	 * </pre>
	 * <code>#getAnnotationName()</code> will return <code>"com.foo.Bar"</code>.
	 * In typical subclasses, <code>#getName()</code> would return
	 * <code>"Thomas"</code>.
	 * 
	 * @see JPA
	 */
	String getAnnotationName();

	/**
	 * Return the corresponding JDT DOM annotation from the specified
	 * AST compilation unit.
	 */
	org.eclipse.jdt.core.dom.Annotation getAstAnnotation(CompilationUnit astRoot);

	/**
	 * Create and add the corresponding Java annotation to the JDT DOM.
	 */
	void newAnnotation();

	/**
	 * Remove the corresponding Java annotation from the JDT DOM.
	 */
	void removeAnnotation();

	/**
	 * Return whether all the annotation's member values are <code>null</code>;
	 * implying the annotation can be removed if it has no semantic value as a
	 * marker annotation.
	 */
	boolean isUnset();

	/**
	 * Store the annotation's state in the specified map, recursing into nested
	 * annotations. Once the state is stored, clear it.
	 * <p>
	 * This is used to save an annotation's state when it is converted from
	 * stand-alone to nested (and vice versa).
	 * During tests, and possibly at other times (albeit
	 * asynchronously), the annotation's state is cleared out when the new
	 * annotation is written to the Java source file; since this will trigger
	 * a Java change event and the annotation will be sync'ed with the, now
	 * empty, Java source code annotation.
	 * 
	 * @see #restoreFrom(Map)
	 */
	// TODO this can probably be removed if we move to "combination" annotation adapters...
	void storeOn(Map<String, Object> map);

	/**
	 * Restore the annotation's state from the specified map, recursing into nested
	 * annotations.
	 * 
	 * @see #storeOn(Map)
	 */
	// TODO this can probably be removed if we move to "combination" annotation adapters...
	void restoreFrom(Map<String, Object> map);
}
