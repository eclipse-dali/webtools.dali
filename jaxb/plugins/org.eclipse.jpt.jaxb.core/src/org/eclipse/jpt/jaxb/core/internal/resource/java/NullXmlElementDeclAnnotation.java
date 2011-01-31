/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementDeclAnnotation;

/**
 * javax.xml.bind.annotation.XmlElementDecl
 */
public final class NullXmlElementDeclAnnotation
	extends NullAnnotation
	implements XmlElementDeclAnnotation
{
	protected NullXmlElementDeclAnnotation(JavaResourceMethod parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	protected XmlElementDeclAnnotation addAnnotation() {
		return (XmlElementDeclAnnotation) super.addAnnotation();
	}


	// ********** XmlEnumAnnotation implementation **********
	
	// ***** name
	public String getName() {
		return null;
	}

	public void setName(String name) {
		if (name != null) {
			this.addAnnotation().setName(name);
		}
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	// ***** namespace
	public String getNamespace() {
		return null;
	}

	public void setNamespace(String namespace) {
		if (namespace != null) {
			this.addAnnotation().setNamespace(namespace);
		}
	}

	public TextRange getNamespaceTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	// ***** defaultValue
	public String getDefaultValue() {
		return null;
	}

	public void setDefaultValue(String defaultValue) {
		if (defaultValue != null) {
			this.addAnnotation().setDefaultValue(defaultValue);
		}
	}

	public TextRange getDefaultValueTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	// ***** scope
	public String getScope() {
		return null;
	}

	public String getFullyQualifiedScopeClassName() {
		return null;
	}

	public void setScope(String scope) {
		if (scope != null) {
			this.addAnnotation().setScope(scope);
		}
	}

	public TextRange getScopeTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	// ***** substitutionHeadName
	public String getSubstitutionHeadName() {
		return null;
	}

	public void setSubstitutionHeadName(String substitutionHeadName) {
		if (substitutionHeadName != null) {
			this.addAnnotation().setSubstitutionHeadName(substitutionHeadName);
		}
	}

	public TextRange getSubstitutionHeadNameTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	// ***** substitutionHeadNamespace
	public String getSubstitutionHeadNamespace() {
		return null;
	}

	public void setSubstitutionHeadNamespace(String substitutionHeadNamespace) {
		if (substitutionHeadNamespace != null) {
			this.addAnnotation().setSubstitutionHeadNamespace(substitutionHeadNamespace);
		}
	}

	public TextRange getSubstitutionHeadNamespaceTextRange(CompilationUnit astRoot) {
		return null;
	}

}
