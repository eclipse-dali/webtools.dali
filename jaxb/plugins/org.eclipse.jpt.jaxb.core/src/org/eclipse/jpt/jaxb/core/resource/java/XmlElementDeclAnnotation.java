/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;

import org.eclipse.jpt.common.core.utility.TextRange;

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
 * @version 3.3
 * @since 3.0
 */
public interface XmlElementDeclAnnotation
		extends QNameAnnotation {
	
	// ***** scope *****
	
	/**
	 * String associated with "scope" property changes.
	 */
	String SCOPE_PROPERTY = "scope"; //$NON-NLS-1$
	
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
	
	/**
	 * Corresponds to the 'scope' element of the XmlElementDecl annotation.
	 * Set to null to remove the element.
	 */
	void setScope(String scope);
	
	/**
	 * Return the {@link TextRange} for the 'scope' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlElementDecl annotation.
	 */
	TextRange getScopeTextRange();
	
	/**
	 * String associated with "fullyQualifiedScopeClassName" property changes.
	 */
	String FULLY_QUALIFIED_SCOPE_CLASS_NAME_PROPERTY = "fullyQualifiedScopeClassName"; //$NON-NLS-1$
	
	/**
	 * Return the fully-qualified scope class name as resolved by the AST's bindings.
	 * <pre>
	 *     &#64;XmlElementDecl(type=XmlElementDecl.GLOBAL.class)
	 * </pre>
	 * will return "javax.xml.bind.annotation.XmlElementDecl.GLOBAL"
	 */
	String getFullyQualifiedScopeClassName();
	
	
	// ***** substitution head namespace *****
	
	/**
	 * String associated with "substitutionHeadNamespace" property changes.
	 */
	String SUBSTITUTION_HEAD_NAMESPACE_PROPERTY = "substitutionHeadNamespace"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'substitutionHeadNamespace' element of the XmlElementDecl annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getSubstitutionHeadNamespace();
	
	/**
	 * Corresponds to the 'substitutionHeadNamespace' element of the XmlElementDecl annotation.
	 * Set to null to remove the element.
	 */
	void setSubstitutionHeadNamespace(String substitutionHeadNamespace);
	
	/**
	 * Return the text range associated with the 'subtitutionHeadNamespace' element.
	 * Return null if the element is absent.
	 */
	TextRange getSubstitutionHeadNamespaceTextRange();
	
	/**
	 * Return the validation text range associated with the 'subtitutionHeadNamespace' element.
	 * Return the text range of this annotation if the element is absent.
	 */
	TextRange getSubstitutionHeadNamespaceValidationTextRange();
	
	/**
	 * Return whether the specified position touches the 'substitutionHeadNamespace' element.
	 * Return false if the element does not exist.
	 */
	boolean substitutionHeadNamespaceTouches(int pos);
	
	
	// ***** substitution head name *****
	
	/**
	 * String associated with "substitutionHeadName" property changes.
	 */
	String SUBSTITUTION_HEAD_NAME_PROPERTY = "substitutionHeadName"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'substitutionHeadName' element of the XmlElementDecl annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getSubstitutionHeadName();
	
	/**
	 * Corresponds to the 'substitutionHeadName' element of the XmlElementDecl annotation.
	 * Set to null to remove the element.
	 */
	void setSubstitutionHeadName(String substitutionHeadName);
	
	/**
	 * Return the text range associated with the 'subtitutionHeadName' element.
	 * Return null if the element is absent.
	 */
	TextRange getSubstitutionHeadNameTextRange();
	
	/**
	 * Return the validation text range associated with the 'subtitutionHeadName' element.
	 * Return the text range of this annotation if the element is absent.
	 */
	TextRange getSubstitutionHeadNameValidationTextRange();
	
	/**
	 * Return whether the specified position touches the 'substitutionHeadName' element.
	 * Return false if the element does not exist.
	 */
	boolean substitutionHeadNameTouches(int pos);
	
	
	// ***** default value *****
	
	/**
	 * String associated with "defaultValue" property changes.
	 */
	String DEFAULT_VALUE_PROPERTY = "defaultValue"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'defaultValue' element of the XmlElementDecl annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getDefaultValue();
	
	/**
	 * Corresponds to the 'defaultValue' element of the XmlElementDecl annotation.
	 * Set to null to remove the element.
	 */
	void setDefaultValue(String defaultValue);
	
	/**
	 * Return the {@link TextRange} for the 'defaultValue' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlElementDecl annotation.
	 */
	TextRange getDefaultValueTextRange();
}
