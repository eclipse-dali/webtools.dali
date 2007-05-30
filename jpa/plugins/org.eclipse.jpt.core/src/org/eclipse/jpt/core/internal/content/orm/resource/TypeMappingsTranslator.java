/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.wst.common.internal.emf.resource.MultiObjectTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class TypeMappingsTranslator extends MultiObjectTranslator 
	implements OrmXmlMapper 
{
	public static final String ENTITY_MAPPINGS_PATH = 
		MAPPED_SUPERCLASS + ',' + ENTITY + ',' + EMBEDDABLE;
    
	private static final OrmPackage JPA_CORE_XML_PKG = OrmPackage.eINSTANCE;
	
	private Map<EObject, Translator> translatorMap;

	public TypeMappingsTranslator() {
		super(ENTITY_MAPPINGS_PATH, JPA_CORE_XML_PKG.getEntityMappingsInternal_TypeMappings());
		this.translatorMap = new HashMap<EObject, Translator>();
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		Translator translator = getDelegateFor(nodeName, readAheadName);
		EObject eObject = translator.createEMFObject(nodeName, readAheadName);
		this.translatorMap.put(eObject, translator);
		return eObject;
	}
	/* (non-Javadoc)
	 * @see MultiObjectTranslator#getDelegateFor(EObject)
	 */
	@Override
	public Translator getDelegateFor(EObject o) {
		Translator translator = translatorMap.get(o);
		if (translator != null) {
			return translator;
		}
		
		switch (o.eClass().getClassifierID()) {
			case OrmPackage.XML_ENTITY_INTERNAL :
				translator = new EntityTranslator();
				((EntityTranslator) translator).setEntity((IEntity) o);
				break;
			case OrmPackage.XML_MAPPED_SUPERCLASS:
				translator = new MappedSuperclassTranslator();	
				break;
			case OrmPackage.XML_EMBEDDABLE:
				translator = new EmbeddableTranslator();
				break;
		}
		if (translator != null) {
			this.translatorMap.put(o, translator);
		}
		
		return translator;
	}
	
	@Override
	public Translator getDelegateFor(String domName, String readAheadName) {
		if (domName.equals(ENTITY)) {
			return new EntityTranslator();
		}
		if (domName.equals(MAPPED_SUPERCLASS)) {
			return new MappedSuperclassTranslator();
		}
		if (domName.equals(EMBEDDABLE)) {
			return new EmbeddableTranslator();
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
