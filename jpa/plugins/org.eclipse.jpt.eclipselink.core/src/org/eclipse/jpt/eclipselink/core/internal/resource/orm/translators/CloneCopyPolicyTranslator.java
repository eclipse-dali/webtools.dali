/*******************************************************************************
 *  Copyright (c) 2009 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class CloneCopyPolicyTranslator extends Translator
	implements EclipseLinkOrmXmlMapper
{	
	private Translator[] children;	
	
	
	public CloneCopyPolicyTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature, END_TAG_NO_INDENT);
	}
	
	@Override
	protected Translator[] getChildren() {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
	}
		
	protected Translator[] createChildren() {
		return new Translator[] {
			createMethodTranslator(),
			createWorkingCopyMethodTranslator(),
		};
	}
	
	protected Translator createMethodTranslator() {
		return new Translator(CLONE_COPY_POLICY__METHOD, ECLIPSELINK_ORM_PKG.getXmlCloneCopyPolicy_Method(), DOM_ATTRIBUTE);
	}
	
	protected Translator createWorkingCopyMethodTranslator() {
		return new Translator(CLONE_COPY_POLICY__WORKING_COPY_METHOD, ECLIPSELINK_ORM_PKG.getXmlCloneCopyPolicy_WorkingCopyMethod(), DOM_ATTRIBUTE);
	}
}
