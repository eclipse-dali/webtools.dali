/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.base.IJpaStructureNode;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.context.base.JpaContextNode;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;


public abstract class XmlTypeMapping<E extends TypeMapping> extends JpaContextNode implements ITypeMapping
{

	protected String class_;
		public static final String CLASS_PROPERTY = "classProperty";
	
	protected AccessType defaultAccess;
		public static final String DEFAULT_ACCESS_PROPERTY = "defaultAccessProperty";

	protected AccessType specifiedAccess;
		public static final String SPECIFIED_ACCESS_PROPERTY = "specifiedAccessProperty";

	protected boolean defaultMetadataComplete;
		public static final String DEFAULT_METADATA_COMPLETE_PROPERTY = "defaultMetadataCompleteProperty";

	protected Boolean specifiedMetadataComplete;
		public static final String SPECIFIED_METADATA_COMPLETE_PROPERTY = "specifiedMetadataCompleteProperty";

	protected IJavaPersistentType javaPersistentType;
		public static final String JAVA_PERSISTENT_TYPE_PROPERTY = "javaPersistentTypeProperty";

	protected E typeMapping;
	
	protected XmlTypeMapping(XmlPersistentType parent) {
		super(parent);
	}

	public boolean isMapped() {
		return true;
	}

	public String tableName() {
		return null;
	}

	public String getClass_() {
		return this.class_;
	}

	public void setClass(String newClass) {
		String oldClass = this.class_;
		this.class_ = newClass;
		this.typeMappingResource().setClassName(newClass);
		firePropertyChanged(CLASS_PROPERTY, oldClass, newClass);
		persistentType().classChanged(oldClass, newClass);
	}
	
	public AccessType getDefaultAccess() {
		return this.defaultAccess;
	}

	protected void setDefaultAccess(AccessType newDefaultAccess) {
		AccessType oldDefaultAccess = this.defaultAccess;
		this.defaultAccess = newDefaultAccess;
		firePropertyChanged(DEFAULT_ACCESS_PROPERTY, oldDefaultAccess, newDefaultAccess);
	}

	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}

	public void setSpecifiedAccess(AccessType newSpecifiedAccess) {
		AccessType oldSpecifiedAccess = this.specifiedAccess;
		this.specifiedAccess = newSpecifiedAccess;
		this.typeMappingResource().setAccess(AccessType.toXmlResourceModel(newSpecifiedAccess));
		firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, oldSpecifiedAccess, newSpecifiedAccess);
	}

	public AccessType getAccess() {
		return (this.getSpecifiedAccess() == null) ? this.getDefaultAccess() : this.getSpecifiedAccess();
	}

	public boolean isMetadataComplete() {
		if (isDefaultMetadataComplete()) {
			//entity-mappings/persistence-unit-metadata/xml-mapping-metadata-complete is specified, then it overrides
			//anything set here
			return Boolean.TRUE;
		}
		return (this.getSpecifiedMetadataComplete() == null) ? this.isDefaultMetadataComplete() : this.getSpecifiedMetadataComplete();
	}

	public boolean isDefaultMetadataComplete() {
		return this.defaultMetadataComplete;
	}
	
	protected void setDefaultMetadataComplete(boolean newDefaultMetadataComplete) {
		boolean oldMetadataComplete = this.defaultMetadataComplete;
		this.defaultMetadataComplete = newDefaultMetadataComplete;
		firePropertyChanged(DEFAULT_METADATA_COMPLETE_PROPERTY, oldMetadataComplete, newDefaultMetadataComplete);
	}
	
	public Boolean getSpecifiedMetadataComplete() {
		return this.specifiedMetadataComplete;
	}
	
	public void setSpecifiedMetadataComplete(Boolean newSpecifiedMetadataComplete) {
		Boolean oldMetadataComplete = this.specifiedMetadataComplete;
		this.specifiedMetadataComplete = newSpecifiedMetadataComplete;
		this.typeMappingResource().setMetadataComplete(newSpecifiedMetadataComplete);
		firePropertyChanged(SPECIFIED_METADATA_COMPLETE_PROPERTY, oldMetadataComplete, newSpecifiedMetadataComplete);
	}

	public XmlPersistentType persistentType() {
		return (XmlPersistentType) parent();
	}

	/**
	 * ITypeMapping is changed and various ITypeMappings may have
	 * common settings.  In this method initialize the new ITypeMapping (this)
	 * fromthe old ITypeMapping (oldMapping)
	 * @param oldMapping
	 */
	public void initializeFrom(XmlTypeMapping<? extends TypeMapping> oldMapping) {
		this.setClass(oldMapping.getClass_());
		this.setSpecifiedAccess(oldMapping.getSpecifiedAccess());
		this.setSpecifiedMetadataComplete(oldMapping.getSpecifiedMetadataComplete());
		this.setDefaultAccess(oldMapping.getDefaultAccess());
		this.setDefaultMetadataComplete(oldMapping.isDefaultMetadataComplete());
	}
	
	public Table primaryDbTable() {
		return null;
	}

	public Table dbTable(String tableName) {
		return null;
	}

	public Schema dbSchema() {
		return null;
	}

//	public ITextRange classTextRange() {
//		IDOMNode classNode = (IDOMNode) DOMUtilities.getChildAttributeNode(node, OrmXmlMapper.CLASS);
//		if (classNode != null) {
//			return buildTextRange(classNode);
//		}
//		return validationTextRange();
//	}
//
//	public ITextRange attributesTextRange() {
//		IDOMNode attributesNode = (IDOMNode) DOMUtilities.getNodeChild(node, OrmXmlMapper.ATTRIBUTES);
//		if (attributesNode != null) {
//			return buildTextRange(attributesNode);
//		}
//		return validationTextRange();
//	}

	/**
	 * type mappings are a sequence in the orm schema. We must keep
	 * the list of type mappings in the appropriate order so the wtp xml 
	 * translators will write them to the xml in that order and they
	 * will adhere to the schema.  
	 * 
	 * Each concrete subclass of XmlTypeMapping must implement this
	 * method and return an int that matches it's order in the schema
	 * @return
	 */
	public abstract int xmlSequence();

	/**
	 * @see ITypeMapping#attributeMappingKeyAllowed(String)
	 * 
	 * Default implementation:  override where needed
	 */
	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return true;
	}

	public Iterator<String> overridableAssociationNames() {
		return EmptyIterator.instance();
	}

	public Iterator<String> overridableAttributeNames() {
		return EmptyIterator.instance();
	}

	public Iterator<String> allOverridableAssociationNames() {
		return EmptyIterator.instance();
	}

	public Iterator<String> allOverridableAttributeNames() {
		return EmptyIterator.instance();
	}

	protected E typeMappingResource() {
		return this.typeMapping;
	}
	
	protected PersistenceUnitMetadata persistenceUnitMetadata() {
		return entityMappings().getPersistenceUnitMetadata();
	}

	protected boolean defaultMetadataComplete() {
		return persistenceUnitMetadata().isXmlMappingMetadataComplete();
	}

	protected AccessType defaultAccess() {
		if (!isMetadataComplete()) {
			if (getJavaPersistentType() != null) {
				if (getJavaPersistentType().hasAnyAttributeMappingAnnotations()) {
					return getJavaPersistentType().access();
				}
				if (persistentType().parentPersistentType() != null) {
					return persistentType().parentPersistentType().access();
				}
			}
		}
		return entityMappings().getAccess();
	}
	
	protected IJavaPersistentType getJavaPersistentType() {
		return this.javaPersistentType;
	}
	
	protected void setJavaPersistentType(IJavaPersistentType newJavaPersistentType) {
		IJavaPersistentType oldJavaPersistentType = this.javaPersistentType;
		this.javaPersistentType = newJavaPersistentType;
		firePropertyChanged(JAVA_PERSISTENT_TYPE_PROPERTY, oldJavaPersistentType, newJavaPersistentType);
	}
	
	protected void initializeJavaPersistentType() {
		JavaPersistentTypeResource persistentTypeResource = jpaProject().javaPersistentTypeResource(getClass_());
		if (persistentTypeResource != null) {
			this.javaPersistentType = createJavaPersistentType(persistentTypeResource);
		}	
	}

	protected void updateJavaPersistentType() {
		JavaPersistentTypeResource persistentTypeResource = jpaProject().javaPersistentTypeResource(getClass_());
		if (persistentTypeResource == null) {
			setJavaPersistentType(null);
		}
		else { 
			if (getJavaPersistentType() != null) {
				getJavaPersistentType().update(persistentTypeResource);
			}
			else {
				setJavaPersistentType(createJavaPersistentType(persistentTypeResource));
			}
		}		
	}
	
	protected IJavaPersistentType createJavaPersistentType(JavaPersistentTypeResource persistentTypeResource) {
		IJavaPersistentType javaPersistentType = jpaFactory().createJavaPersistentType(this);
		javaPersistentType.initializeFromResource(persistentTypeResource);
		return javaPersistentType;
	}

	public void initialize(E typeMapping) {
		this.typeMapping = typeMapping;
		this.class_ = typeMapping.getClassName();
		this.initializeJavaPersistentType();
		this.specifiedMetadataComplete = this.metadataComplete(typeMapping);
		this.defaultMetadataComplete = this.defaultMetadataComplete();
		this.specifiedAccess = AccessType.fromXmlResourceModel(typeMapping.getAccess());
		this.defaultAccess = this.defaultAccess();
	}
	
	public void update(E typeMapping) {
		this.typeMapping = typeMapping;
		this.setClass(typeMapping.getClassName());
		this.updateJavaPersistentType();
		this.setSpecifiedMetadataComplete(this.metadataComplete(typeMapping));
		this.setDefaultMetadataComplete(this.defaultMetadataComplete());
		this.setSpecifiedAccess(AccessType.fromXmlResourceModel(typeMapping.getAccess()));
		this.setDefaultAccess(this.defaultAccess());
	}
	
	protected Boolean metadataComplete(TypeMapping typeMapping) {
		return typeMapping.getMetadataComplete();
	}


	public abstract void removeFromResourceModel(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings);
	
	public abstract E addToResourceModel(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings);

	
	// *************************************************************************
	
	public IJpaStructureNode structureNode(int offset) {
		if (this.typeMapping.containsOffset(offset)) {
			return persistentType();
		}
		return null;
	}
	
	public ITextRange selectionTextRange() {
		return this.typeMapping.selectionTextRange();
	}

	public boolean containsOffset(int textOffset) {
		if (typeMapping == null) {
			return false;
		}
		return typeMapping.containsOffset(textOffset);
	}
}
