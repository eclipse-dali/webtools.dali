/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbType;
import org.eclipse.jpt.jaxb.core.internal.resource.java.source.SourceNode;
import org.eclipse.jpt.jaxb.core.resource.java.AbstractJavaResourceType;
import org.eclipse.jst.j2ee.model.internal.validation.ValidationCancelledException;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractJavaType
		extends AbstractJavaJaxbContextNode
		implements JaxbType {
	
	protected final AbstractJavaResourceType resourceType;

	
	protected AbstractJavaType(JaxbContextRoot parent, AbstractJavaResourceType resourceType) {
		super(parent);
		this.resourceType = resourceType;
		
	}
	
	
	// **************** AbstractJaxbNode impl *********************************
	
	@Override
	public IResource getResource() {
		return this.resourceType.getFile();
	}
	
	
	// *********** JaxbType impl ***********
	
	public AbstractJavaResourceType getJavaResourceType() {
		return this.resourceType;
	}
	
	public String getFullyQualifiedName() {
		return this.resourceType.getQualifiedName();
	}
	
	public String getTypeQualifiedName() {
		String packageName = getPackageName();
		return (packageName.length() == 0) ? getFullyQualifiedName() : getFullyQualifiedName().substring(packageName.length() + 1);
	}
	
	public String getSimpleName() {
		return this.resourceType.getName();
	}
	
	public String getPackageName() {
		return this.resourceType.getPackageName();
	}
	
	public JaxbPackage getJaxbPackage() {
		return getContextRoot().getPackage(getPackageName());
	}
	
	
	// **************** misc **************************************************
	
	protected CompilationUnit buildASTRoot() {
		return this.resourceType.getJavaResourceCompilationUnit().buildASTRoot();
	}
	
	
	// **************** validation ********************************************
	
	/**
	 * Override as needed
	 */
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getJavaResourceType().getNameTextRange(astRoot);
	}
	
	public void validate(List<IMessage> messages, IReporter reporter) {
		if (reporter.isCancelled()) {
			throw new ValidationCancelledException();
		}
		// TODO temporary hack since we don't know yet where to put
		// any messages for types in another project
		IFile file = this.resourceType.getFile();
		// 'file' will be null if the type is "external" and binary;
		// the file will be in a different project if the type is "external" and source;
		// the type will be binary if it is in a JAR in the current project
		if ((file != null) 
				&& file.getProject().equals(getJaxbProject().getProject()) 
				&& (this.resourceType instanceof SourceNode)) {
			// build the AST root here to pass down
			this.validate(messages, reporter, this.buildASTRoot());
		}
	}
}
