/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IAttributeMapping;
import org.eclipse.jpt.core.internal.context.base.IMultiRelationshipMapping;
import org.eclipse.jpt.core.internal.resource.orm.MapKey;
import org.eclipse.jpt.core.internal.resource.orm.MultiRelationshipMapping;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.utility.internal.StringTools;


public abstract class XmlMultiRelationshipMapping<T extends MultiRelationshipMapping>
	extends XmlRelationshipMapping<T>
	implements IMultiRelationshipMapping
{

	protected String mappedBy;

	protected String orderBy;//TODO change this to defaultOrderBy and specifiedOrderBy?

	protected final XmlJoinTable joinTable;

	protected String mapKey;

	protected XmlMultiRelationshipMapping(XmlPersistentAttribute parent) {
		super(parent);
		this.joinTable = new XmlJoinTable(this);
	}

	public String getMappedBy() {
		return this.mappedBy;
	}

	public void setMappedBy(String newMappedBy) {
		String oldMappedBy = this.mappedBy;
		this.mappedBy = newMappedBy;
		attributeMapping().setMappedBy(newMappedBy);
		firePropertyChanged(MAPPED_BY_PROPERTY, oldMappedBy, newMappedBy);
	}

	public String getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(String newOrderBy) {
		String oldOrderBy = this.orderBy;
		this.orderBy = newOrderBy;
		attributeMapping().setOrderBy(newOrderBy);
		firePropertyChanged(ORDER_BY_PROPERTY, oldOrderBy, newOrderBy);
	}

	public boolean isNoOrdering() {
		return getOrderBy() == null;
	}

	public void setNoOrdering() {
		setOrderBy(null);
	}

	//hmm, this looks odd TODO for default orderBy for pk ordering
	public boolean isOrderByPk() {
		return "".equals(getOrderBy());
	}

	public void setOrderByPk() {
		setOrderBy("");
	}

	public boolean isCustomOrdering() {
		return !StringTools.stringIsEmpty(getOrderBy());
	}

//	public ITextRange mappedByTextRange() {
//		if (node == null) {
//			return typeMapping().validationTextRange();
//		}
//		IDOMNode mappedByNode = (IDOMNode) DOMUtilities.getChildAttributeNode(node, OrmXmlMapper.MAPPED_BY);
//		return (mappedByNode == null) ? validationTextRange() : buildTextRange(mappedByNode);
//	}
	
	public XmlJoinTable getJoinTable() {
		return this.joinTable;
	}

	public boolean isJoinTableSpecified() {
		return getJoinTable().isSpecified();
	}

	public String getMapKey() {
		return this.mapKey;
	}

	public void setMapKey(String newMapKey) {
		String oldMapKey = this.mapKey;
		this.mapKey = newMapKey;
		if (oldMapKey != newMapKey) {
			if (this.mapKeyResource() != null) {
				this.mapKeyResource().setName(newMapKey);						
				if (this.mapKeyResource().isAllFeaturesUnset()) {
					removeMapKeyResource();
				}
			}
			else if (newMapKey != null) {
				addMapKeyResource();
				mapKeyResource().setName(newMapKey);
			}
		}
		firePropertyChanged(MAP_KEY_PROPERTY, oldMapKey, newMapKey);
	}
	
	protected void setMapKey_(String newMapKey) {
		String oldMapKey = this.mapKey;
		this.mapKey = newMapKey;
		firePropertyChanged(MAP_KEY_PROPERTY, oldMapKey, newMapKey);
	}
	
	protected MapKey mapKeyResource() {
		return attributeMapping().getMapKey();
	}
	
	protected void removeMapKeyResource() {
		attributeMapping().setMapKey(null);
	}
	
	protected void addMapKeyResource() {
		attributeMapping().setMapKey(OrmFactory.eINSTANCE.createMapKey());
	}

	public Iterator<String> candidateMapKeyNames() {
		return this.allTargetEntityAttributeNames();
	}

//	public void refreshDefaults(DefaultsContext defaultsContext) {
//		super.refreshDefaults(defaultsContext);
//		// TODO
//		//		if (isOrderByPk()) {
//		//			refreshDefaultOrderBy(defaultsContext);
//		//		}
//	}
//
//	//primary key ordering when just the @OrderBy annotation is present
//	protected void refreshDefaultOrderBy(DefaultsContext defaultsContext) {
//		IEntity targetEntity = getResolvedTargetEntity();
//		if (targetEntity != null) {
//			setOrderBy(targetEntity.primaryKeyAttributeName() + " ASC");
//		}
//	}
//
//	//TODO copied from JavaMultiRelationshipMapping
//	/**
//	 * extract the element type from the specified container signature and
//	 * convert it into a reference entity type name;
//	 * return null if the type is not a valid reference entity type (e.g. it's
//	 * another container or an array or a primitive or other Basic type)
//	 */
//	@Override
//	protected String javaDefaultTargetEntity(ITypeBinding typeBinding) {
//		String typeName = super.javaDefaultTargetEntity(typeBinding);
//		return JavaRelationshipMapping.typeNamedIsContainer(typeName) ? this.javaDefaultTargetEntityFromContainer(typeBinding) : null;
//	}
//
//	protected String javaDefaultTargetEntityFromContainer(ITypeBinding typeBinding) {
//		return JavaMultiRelationshipMapping.javaDefaultTargetEntityFromContainer(typeBinding);
//	}
	
	public ITextRange mappedByTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean mappedByIsValid(IAttributeMapping mappedByMapping) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void initialize(T multiRelationshipMapping) {
		super.initialize(multiRelationshipMapping);
		this.mappedBy = multiRelationshipMapping.getMappedBy();
		this.mapKey = this.mapKey(multiRelationshipMapping);
		this.orderBy = this.orderBy(multiRelationshipMapping);
		this.joinTable.initialize(multiRelationshipMapping);
	}
	
	@Override
	public void update(T multiRelationshipMapping) {
		super.update(multiRelationshipMapping);
		this.setMappedBy(multiRelationshipMapping.getMappedBy());
		this.setMapKey_(this.mapKey(multiRelationshipMapping));
		this.setOrderBy(this.orderBy(multiRelationshipMapping));
		this.joinTable.update(multiRelationshipMapping);
	}
	
	protected String mapKey(T multiRelationshipMapping) {
		return attributeMapping().getMapKey() == null ? null : attributeMapping().getMapKey().getName();
	}
	
	protected String orderBy(T multiRelationshipMapping) {
		return attributeMapping().getOrderBy();
	}
}
