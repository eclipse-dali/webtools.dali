/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.common.internal.emf.resource.MultiObjectTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * Created this class so there would be a 1-1 association between 
 * SecondaryTable and SecondaryTableTranslator.  Without this the state
 * stored on SecondaryTableTranslator will be invalid for multiple SecondaryTables.
 * see bug 188901.
 * 
 * TODO 189767 - memory leak if a SecondaryTable is removed from the model, it is still
 * stored along with its translator in the translator map
 */
public class SecondaryTablesTranslator extends MultiObjectTranslator implements OrmXmlMapper
{

	private IEntity entity;

	private Map<EObject, SecondaryTableTranslator> translatorMap;
	
	protected static final JpaCoreMappingsPackage MAPPINGS_PKG = 
		JpaCoreMappingsPackage.eINSTANCE;
		
	public SecondaryTablesTranslator() {
		super(SECONDARY_TABLE, MAPPINGS_PKG.getIEntity_SpecifiedSecondaryTables());
		this.translatorMap = new HashMap<EObject, SecondaryTableTranslator>();
	}
	
	
	protected IEntity getEntity() {
		return this.entity;
	}
	
	void setEntity(IEntity entity) {
		this.entity = entity;
		for (SecondaryTableTranslator translator : translatorMap.values()) {
			translator.setEntity(entity);
		}
	}

	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		SecondaryTableTranslator translator = (SecondaryTableTranslator) getDelegateFor(nodeName, readAheadName);
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
			case OrmPackage.XML_SECONDARY_TABLE :
				SecondaryTableTranslator secondaryTableTranslator =  new SecondaryTableTranslator();
				secondaryTableTranslator.setEntity(getEntity());
				return secondaryTableTranslator;
		}
		
		return null;
	}
	
	@Override
	public Translator getDelegateFor(String domName, String readAheadName) {
		if (domName.equals(SECONDARY_TABLE)) {
			SecondaryTableTranslator secondaryTableTranslator =  new SecondaryTableTranslator();
			secondaryTableTranslator.setEntity(getEntity());
			return secondaryTableTranslator;
		}
		throw new IllegalStateException("Illegal dom name: " + domName); //$NON-NLS-1$
	}
}
