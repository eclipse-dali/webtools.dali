/*******************************************************************************
 *  Copyright (c) 2011 Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbTransientType;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTransientAnnotation;


public class GenericJavaTransientType
		extends AbstractJavaType
		implements JaxbTransientType {


	public GenericJavaTransientType(JaxbContextRoot parent, JavaResourceType resourceType) {
		super(parent, resourceType);
	}

	@Override
	public JavaResourceType getJavaResourceType() {
		return (JavaResourceType) super.getJavaResourceType();
	}


	// ********** xml transient annotation **********

	protected XmlTransientAnnotation getXmlTransientAnnotation() {
		return (XmlTransientAnnotation) this.getJavaResourceType().getNonNullAnnotation(XmlTransientAnnotation.ANNOTATION_NAME);
	}
	
	
	// ********** JaxbType impl **********
	
	public Kind getKind() {
		return Kind.TRANSIENT;
	}


	// **************** validation ********************************************
	
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getXmlTransientAnnotation().getTextRange(astRoot);
	}
}
