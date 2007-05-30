/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.mappings.IBasic;
import org.eclipse.jpt.core.internal.mappings.IEmbedded;
import org.eclipse.jpt.core.internal.mappings.IId;
import org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class AttributeMappingsTranslator extends MultiObjectDependencyTranslator
	implements OrmXmlMapper 
{
	public static final String ATTRIBUTES_PATH = 
		ATTRIBUTES + '/' + ID + ',' + EMBEDDED_ID + ','+ BASIC + ',' + VERSION +',' + MANY_TO_ONE + "," + ONE_TO_MANY + ',' + ONE_TO_ONE + ',' + MANY_TO_MANY + ',' + EMBEDDED+ ',' + TRANSIENT;
    
	private static final OrmPackage JPA_CORE_XML_PKG = OrmPackage.eINSTANCE;

	public AttributeMappingsTranslator() {
		super(ATTRIBUTES_PATH, JPA_CORE_XML_PKG.getXmlPersistentType_SpecifiedAttributeMappings(), JPA_CORE_XML_PKG.getXmlTypeMapping_PersistentType());
	}
	
	//TODO we're not putting the translator in the translator map in this situation
	public Translator getDelegateFor(EObject o) {
		Translator translator = super.getDelegateFor(o);
		if (translator != null) {
			return translator;
		}
		switch (o.eClass().getClassifierID()) {
			case OrmPackage.XML_ID :
				translator = new IdTranslator();
				((IdTranslator) translator).setId(((IId) o));
				break;
			case OrmPackage.XML_BASIC :
				translator = new BasicTranslator();
				((BasicTranslator) translator).setBasic(((IBasic) o));
				break;		
			case OrmPackage.XML_ONE_TO_MANY :
				translator = new OneToManyTranslator();
				((OneToManyTranslator) translator).setMapping((IMultiRelationshipMapping) o);
				break;
			case OrmPackage.XML_MANY_TO_MANY :
				translator = new ManyToManyTranslator();
				((ManyToManyTranslator) translator).setMapping((IMultiRelationshipMapping) o);
				break;
			case OrmPackage.XML_MANY_TO_ONE :
				translator = new ManyToOneTranslator();
				break;
			case OrmPackage.XML_TRANSIENT :
				translator = new TransientTranslator();
				break;
			case OrmPackage.XML_EMBEDDED :
				translator = new EmbeddedTranslator();
				((EmbeddedTranslator) translator).setEmbedded((IEmbedded) o);
				break;
			case OrmPackage.XML_EMBEDDED_ID :
				translator = new EmbeddedIdTranslator();
				break;
			case OrmPackage.XML_ONE_TO_ONE :
				translator = new OneToOneTranslator();
				break;
			case OrmPackage.XML_VERSION :
				translator = new VersionTranslator();
				break;
		}
		
		return translator;
	}
	

	@Override
	public Translator getDelegateFor(String domName, String readAheadName) {
		if (domName.equals(ID)) {
			return new IdTranslator();
		}
		if (domName.equals(BASIC)) {
			return new BasicTranslator();
		}
		if (domName.equals(MANY_TO_ONE)) {
			return new ManyToOneTranslator();
		}
		if (domName.equals(ONE_TO_MANY)) {
			return new OneToManyTranslator();
		}
		if (domName.equals(MANY_TO_MANY)) {
			return new ManyToManyTranslator();
		}
		if (domName.equals(TRANSIENT)) {
			return new TransientTranslator();
		}
		if (domName.equals(EMBEDDED)) {
			return new EmbeddedTranslator();
		}
		if (domName.equals(EMBEDDED_ID)) {
			return new EmbeddedIdTranslator();
		}
		if (domName.equals(ONE_TO_ONE)) {
			return new OneToOneTranslator();
		}
		if (domName.equals(VERSION)) {
			return new VersionTranslator();
		}
		throw new IllegalStateException("Illegal dom name: " + domName); //$NON-NLS-1$
	}
	
	@Override
	public boolean isDependencyParent() {
		return true;
	}
	
	@Override
	public EObject basicGetDependencyObject(EObject parent) {
		Translator delegate = getDelegateFor(parent);
		
		if (delegate != null) {
			return delegate.basicGetDependencyObject(parent);
		}
		else {
			return super.basicGetDependencyObject(parent);
		}
	}
}
