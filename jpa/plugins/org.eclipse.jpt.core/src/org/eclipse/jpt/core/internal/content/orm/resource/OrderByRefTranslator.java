/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.mappings.OrderingType;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class OrderByRefTranslator extends Translator implements OrmXmlMapper
{
	protected static final OrmPackage JPA_CORE_XML_PKG = 
		OrmPackage.eINSTANCE;

	private static Translator[] children;
	
	private IMultiRelationshipMapping mapping;
	
	public OrderByRefTranslator() {
		super(ORDER_BY, JPA_CORE_XML_PKG.getXmlMultiRelationshipMappingForXml_OrderByForXml());
		this.fStyle = EMPTY_TAG;
	}
		
	
	public Translator[] getChildren(Object target, int versionID) {
		if (children == null) {
			children = createChildren();
		}
		return children;
	}
	
	private static Translator[] createChildren() {
		return new Translator[] {
			new Translator(TEXT_ATTRIBUTE_VALUE, JpaCoreMappingsPackage.eINSTANCE.getIOrderBy_Value())
		};
	}
	
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return this.mapping.getOrderBy();
	}
	
	protected IMultiRelationshipMapping getMapping() {
		return this.mapping;
	}
	
	void setMapping(IMultiRelationshipMapping mapping) {
		this.mapping = mapping;
	}
	
	@Override
	public Object convertStringToValue(String strValue, EObject owner) {
		if (strValue == null) {
			if (mapping.getOrderBy().getType() == OrderingType.PRIMARY_KEY) {
				return Boolean.TRUE;
			}
		}
		return super.convertStringToValue(strValue, owner);
	}
	
	public boolean isSetMOFValue(EObject emfObject) {
		boolean isSet = feature != null && emfObject.eIsSet(feature);
		return isSet && (mapping.getOrderBy().getType() == OrderingType.PRIMARY_KEY);
	}
	
	@Override
	public boolean isManagedByParent() {
		return (mapping.getOrderBy().getType() == OrderingType.PRIMARY_KEY);
	}

}
