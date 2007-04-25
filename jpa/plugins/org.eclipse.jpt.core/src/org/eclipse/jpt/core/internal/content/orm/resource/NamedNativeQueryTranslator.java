/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.content.orm.OrmFactory;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class NamedNativeQueryTranslator extends Translator implements OrmXmlMapper
{
	protected static final JpaCoreMappingsPackage MAPPINGS_PKG = 
		JpaCoreMappingsPackage.eINSTANCE;
	protected static final OrmPackage JPA_CORE_XML_PKG = 
		OrmPackage.eINSTANCE;
	
	
	private Translator[] children;	
	
	public NamedNativeQueryTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature, NO_STYLE);
	}
	
	public Translator[] getChildren(Object target, int versionID) {
		if (children == null) {
			children = createChildren();
		}
		return children;
	}
		
	
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createResultClassTranslator(),
			createResultSetMappingTranslator(),
			createQueryTranslator(),
			createQueryHintTranslator(),
		};
	}

	protected Translator createNameTranslator() {
		return new Translator(NAMED_NATIVE_QUERY__NAME, MAPPINGS_PKG.getIQuery_Name(), DOM_ATTRIBUTE);
	}
	
	protected Translator createQueryTranslator() {
		return new Translator(NAMED_NATIVE_QUERY__QUERY, MAPPINGS_PKG.getIQuery_Query(), NO_STYLE);
	}
	
	protected Translator createResultClassTranslator() {
		return new Translator(NAMED_NATIVE_QUERY__RESULT_CLASS, MAPPINGS_PKG.getINamedNativeQuery_ResultClass(), DOM_ATTRIBUTE);
	}
	
	protected Translator createResultSetMappingTranslator() {
		return new Translator(NAMED_NATIVE_QUERY__RESULT_SET_MAPPING, MAPPINGS_PKG.getINamedNativeQuery_ResultSetMapping(), DOM_ATTRIBUTE);
	}
	
	protected Translator createQueryHintTranslator() {
		return new QueryHintTranslator(NAMED_NATIVE_QUERY__HINT, MAPPINGS_PKG.getIQuery_Hints());
	}

	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return OrmFactory.eINSTANCE.createXmlNamedQuery();
	}

}
