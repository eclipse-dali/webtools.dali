/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Corresponds to the JAXB annotation
 * javax.xml.bind.annotation.XmlElementDecl
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface XmlElementDeclAnnotation
	extends Annotation
{
	String ANNOTATION_NAME = JAXB.XML_ELEMENT_DECL;

	/**
	 * Corresponds to the 'name' element of the XmlElementDecl annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getName();
		String NAME_PROPERTY = "name"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'name' element of the XmlElementDecl annotation.
	 * Set to null to remove the element.
	 */
	void setName(String name);

	/**
	 * Return the {@link TextRange} for the 'name' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlElementDecl annotation.
	 */
	TextRange getNameTextRange(CompilationUnit astRoot);

	/**
	 * Corresponds to the 'namespace' element of the XmlElementDecl annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getNamespace();
		String NAMESPACE_PROPERTY = "namespace"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'namespace' element of the XmlElementDecl annotation.
	 * Set to null to remove the element.
	 */
	void setNamespace(String namespace);

	/**
	 * Return the {@link TextRange} for the 'namespace' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlElementDecl annotation.
	 */
	TextRange getNamespaceTextRange(CompilationUnit astRoot);

	/**
	 * Corresponds to the 'defaultValue' element of the XmlElementDecl annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getDefaultValue();
		String DEFAULT_VALUE_PROPERTY = "defaultValue"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'defaultValue' element of the XmlElementDecl annotation.
	 * Set to null to remove the element.
	 */
	void setDefaultValue(String defaultValue);

	/**
	 * Return the {@link TextRange} for the 'defaultValue' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlElementDecl annotation.
	 */
	TextRange getDefaultValueTextRange(CompilationUnit astRoot);

	/**
	 * Corresponds to the 'scope' element of the XmlElementDecl annotation.
	 * Return null if the element does not exist in Java.
	 * Return the portion of the value preceding ".class".
	 * <pre>
	 *     &#64;XmlElementDecl(type=XmlElementDecl.GLOBAL.class)
	 * </pre>
	 * will return "XmlElementDecl.GLOBAL"
	 */
	String getScope();
		String SCOPE_PROPERTY = "scope"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'scope' element of the XmlElementDecl annotation.
	 * Set to null to remove the element.
	 */
	void setScope(String scope);

	/**
	 * Return the {@link TextRange} for the 'scope' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlElementDecl annotation.
	 */
	TextRange getScopeTextRange(CompilationUnit astRoot);

	/**
	 * Return the fully-qualified scope class name as resolved by the AST's bindings.
	 * <pre>
	 *     &#64;XmlElementDecl(type=XmlElementDecl.GLOBAL.class)
	 * </pre>
	 * will return "javax.xml.bind.annotation.XmlElementDecl.GLOBAL"
	 */
	String getFullyQualifiedScopeClassName();
		String FULLY_QUALIFIED_SCOPE_CLASS_NAME_PROPERTY = "fullyQualifiedScopeClassName"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'substitutionHeadName' element of the XmlElementDecl annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getSubstitutionHeadName();
		String SUBSTITUTION_HEAD_NAME_PROPERTY = "substitutionHeadName"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'substitutionHeadName' element of the XmlElementDecl annotation.
	 * Set to null to remove the element.
	 */
	void setSubstitutionHeadName(String substitutionHeadName);

	/**
	 * Return the {@link TextRange} for the 'substitutionHeadName' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlElementDecl annotation.
	 */
	TextRange getSubstitutionHeadNameTextRange(CompilationUnit astRoot);

	/**
	 * Corresponds to the 'substitutionHeadNamespace' element of the XmlElementDecl annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getSubstitutionHeadNamespace();
		String SUBSTITUTION_HEAD_NAMESPACE_PROPERTY = "substitutionHeadNamespace"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'substitutionHeadNamespace' element of the XmlElementDecl annotation.
	 * Set to null to remove the element.
	 */
	void setSubstitutionHeadNamespace(String substitutionHeadNamespace);

	/**
	 * Return the {@link TextRange} for the 'substitutionHeadNamespace' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlElementDecl annotation.
	 */
	TextRange getSubstitutionHeadNamespaceTextRange(CompilationUnit astRoot);

}
