/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform.base;

import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.core.internal.IJpaAnnotationProvider;
import org.eclipse.jpt.core.internal.IJpaFactory;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.IResourceModel;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class BaseJpaPlatform implements IJpaPlatform
{
	private String id;
	
	protected IJpaFactory jpaFactory;
	
	protected IJpaAnnotationProvider annotationProvider;
	
	
	protected BaseJpaPlatform() {
		super();
	}
	
	
	public String getId() {
		return this.id;
	}
	
	/**
	 * *************
	 * * IMPORTANT *   For INTERNAL use only !!
	 * *************
	 * 
	 * @see IJpaPlatform#setId(String)
	 */
	public void setId(String theId) {
		this.id = theId;
	}
	
	
	// **************** Model construction / updating **************************
	
	public IJpaFactory jpaFactory() {
		if (jpaFactory == null) {
			jpaFactory = buildJpaFactory();
		}
		return jpaFactory;
	}
	
	protected abstract IJpaFactory buildJpaFactory();
	
	public IJpaFile buildJpaFile(IJpaProject jpaProject, IFile file) {
		if (jpaFactory().hasRelevantContent(file)) {
			IResourceModel resourceModel = jpaFactory().buildResourceModel(jpaProject, file);
			return jpaFactory().createJpaFile(jpaProject, file, resourceModel);
		}
		
		return null;
	}
	
	
	// **************** java annotation support ********************************
	
	public IJpaAnnotationProvider annotationProvider() {
		if (annotationProvider == null) {
			annotationProvider = buildAnnotationProvider();
		}
		return annotationProvider;
	}
	
	protected abstract IJpaAnnotationProvider buildAnnotationProvider();
	
	
	// **************** Validation *********************************************
	
	public void addToMessages(List<IMessage> messages) {
		// TODO Auto-generated method stub
		
	}
}
