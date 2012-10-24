/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.oxm;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.TypeDeclarationTools;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessageBuilder;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessages;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OxmJavaTypeImpl
		extends AbstractJaxbContextNode
		implements OxmJavaType {
	
	protected EJavaType eJavaType;
	
	protected String specifiedName;
	protected String qualifiedName;
	
	
	public OxmJavaTypeImpl(OxmXmlBindings parent, EJavaType eJavaType) {
		super(parent);
		this.eJavaType = eJavaType;
		this.specifiedName = buildSpecifiedName();
		this.qualifiedName = buildQualifiedName();
	}
	
	
	public OxmXmlBindings getXmlBindings() {
		return (OxmXmlBindings) getParent();
	}
	
	public EJavaType getEJavaType() {
		return this.eJavaType;
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		setSpecifiedName_(buildSpecifiedName());
		setQualifiedName_(buildQualifiedName());
	}
	
	
	// ***** name *****
	
	public String getSpecifiedName() {
		return this.specifiedName;
	}
	
	public void setSpecifiedName(String newName) {
		this.eJavaType.setName(newName);
		setSpecifiedName_(newName);
	}
	
	protected void setSpecifiedName_(String newName) {
		String oldName = this.specifiedName;
		this.specifiedName = newName;
		firePropertyChanged(SPECIFIED_NAME_PROPERTY, oldName, newName);
	}
	
	protected String buildSpecifiedName() {
		return this.eJavaType.getName();
	}
	
	public String getQualifiedName() {
		return this.qualifiedName;
	}
	
	protected void setQualifiedName_(String newName) {
		String oldName = this.qualifiedName;
		this.qualifiedName = newName;
		firePropertyChanged(QUALIFIED_NAME_PROPERTY, oldName, newName);
	}
	
	protected String buildQualifiedName() {
		return getXmlBindings().getQualifiedName(this.specifiedName);
	}
	
	public String getSimpleName() {
		return TypeDeclarationTools.simpleName(this.qualifiedName);
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange() {
		TextRange textRange = this.eJavaType.getValidationTextRange();
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange();
	}
	
	protected TextRange getNameTextRange() {
		return this.eJavaType.getNameTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		validateName(messages, reporter);
		
	}
	
	protected void validateName(List<IMessage> messages, IReporter reporter) {
		// type name must be specified
		if (StringTools.isBlank(this.specifiedName)) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							ELJaxbValidationMessages.OXM_JAVA_TYPE__NAME_NOT_SPECIFIED,
							this,
							getNameTextRange()));
			return;
		}
		
		// package name must be uniform across oxm file
		String packageName = TypeDeclarationTools.packageName(this.specifiedName);
		if (! StringTools.isBlank(packageName) && ! ObjectTools.equals(packageName, getXmlBindings().getPackageName())) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							ELJaxbValidationMessages.OXM_JAVA_TYPE__PACKAGE_NAME_NOT_UNIFORM,
							this,
							getNameTextRange()));
		}
	}
}
