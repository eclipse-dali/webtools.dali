/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ClassName;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.CollectionMapping;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.Accessor;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaNullAttributeMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaPersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.internal.plugin.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAttributeMapping;

public class VirtualJavaPersistentAttribute
		extends AbstractJavaJpaContextNode
		implements JavaPersistentAttribute2_0, JavaEclipseLinkPersistentAttribute {
	
	private final XmlAttributeMapping xmlAttributeMapping;
	
	private final JavaAttributeMapping attributeMapping;

	private JpaContainerDefinition jpaContainerDefinition = JpaContainerDefinition.Null.instance();

	public VirtualJavaPersistentAttribute(OrmPersistentType parent, XmlAttributeMapping xmlAttributeMapping) {
		super(parent);
		this.xmlAttributeMapping = xmlAttributeMapping;
		this.attributeMapping = new GenericJavaNullAttributeMapping(this);
	}

	@Override
	public void update() {
		super.update();
		this.updateJpaContainerDefinition();
	}

	public XmlAttributeMapping getXmlAttributeMapping() {
		return this.xmlAttributeMapping;
	}

	public JavaAttributeMapping getMapping() {
		return this.attributeMapping;
	}

	public JavaAttributeMapping setMappingKey(String key) {
		throw new UnsupportedOperationException("cannot set anything on a virtual java persistent attribute"); //$NON-NLS-1$
	}

	public Accessor getAccessor() {
		return null;
	}

	public JavaResourceAttribute getResourceAttribute() {
		return null;
	}

	public boolean isFor(JavaResourceField resourceField) {
		return false;
	}

	public boolean isFor(JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
		return false;
	}

	public boolean contains(int offset) {
		throw new UnsupportedOperationException();
	}

	public String getTypeName() {
		String typeName = this.xmlAttributeMapping.getAttributeType();
		return typeName == null ? null : this.getEntityMappings().getFullyQualifiedName(typeName);
	}
	
	public String getTypeName(PersistentType contextType) {
		// only ever has its specified type name
		return getTypeName();
	}
	
	public boolean typeIsBasic() {
		return false;//not valid for a default basic mapping, specified in orm.xml
	}

	public String getSingleReferenceTargetTypeName() {
		return null; //used for building default target entity/embeddable, must be specified in a virtual mapping
	}

	public String getMultiReferenceTargetTypeName() {
		return null; //used for building default target entity/target class, must be specified in a virtual mapping
	}

	public String getMultiReferenceMapKeyTypeName() {
		return null; //used for building default map key class, must be specified in a virtual mapping
	}

	public String getName() {
		return this.xmlAttributeMapping.getName();
	}

	public String getMappingKey() {
		return null;
	}

	public String getDefaultMappingKey() {
		return null;
	}

	public boolean isVirtual() {
		throw new UnsupportedOperationException("Owing orm persistent attribute is specified, this should not be called."); //$NON-NLS-1$
	}

	public JavaPersistentAttribute getJavaPersistentAttribute() {
		return this;
	}


	// ********** JpaStructureNode implementation **********


	public JpaStructureNode getStructureNode(int textOffset) {
		throw new UnsupportedOperationException("There is no resource for a virtual java persistent attribute"); //$NON-NLS-1$
	}

	public TextRange getSelectionTextRange() {
		throw new UnsupportedOperationException("There is no resource for a virtual java persistent attribute"); //$NON-NLS-1$
	}

	public ContextType getContextType() {
		throw new UnsupportedOperationException("There is no resource for a virtual java persistent attribute"); //$NON-NLS-1$
	}

	public Class<? extends JpaStructureNode> getType() {
		throw new UnsupportedOperationException("There is no resource for a virtual java persistent attribute"); //$NON-NLS-1$
	}

	public void dispose() {
		throw new UnsupportedOperationException("There is no resource for a virtual java persistent attribute"); //$NON-NLS-1$
	}

	public AccessType getAccess() {
		return null;
	}

	public AccessType getSpecifiedAccess() {
		return null;
	}

	public void setSpecifiedAccess(AccessType newSpecifiedAccess) {
		throw new UnsupportedOperationException("cannot set anything on a virtual java persistent attribute"); //$NON-NLS-1$
	}

	public AccessType getDefaultAccess() {
		return null;
	}

	public TextRange getValidationTextRange() {
		throw new UnsupportedOperationException();
	}


	public boolean typeIsDateOrCalendar() {
		String typeName = this.getTypeName();
		if (typeName == null) {
			return false;
		}
		return JDTTools.typeIsSubType(this.getJavaProject(), typeName, DATE_TYPE_NAME)
			|| JDTTools.typeIsSubType(this.getJavaProject(), typeName, CALENDAR_TYPE_NAME);
	}

	public boolean typeIsSerializable() {
		String typeName = this.getTypeName();
		if (typeName == null) {
			return false;
		}
		return JDTTools.typeIsSubType(this.getJavaProject(), typeName, JDTTools.SERIALIZABLE_CLASS_NAME);
	}

	public boolean typeIsValidForVariableOneToOne() {
		String typeName = this.getTypeName();
		if (typeName == null) {
			return false;
		}
		IType type = JDTTools.findType(getJavaProject(), typeName);
		try {
			return type != null &&
					type.isInterface() &&
					this.interfaceIsValidForVariableOneToOne();
		}
		catch (JavaModelException e) {
			JptJpaEclipseLinkCorePlugin.instance().logError(e);
			return false;
		}
	}

	protected boolean interfaceIsValidForVariableOneToOne() {
		return ! this.interfaceIsInvalidForVariableOneToOne();
	}

	// TODO we could probably add more interfaces to this list...
	protected boolean interfaceIsInvalidForVariableOneToOne() {
		String interfaceName = this.getTypeName();
		return (interfaceName == null) ||
				this.typeIsContainer() ||
				interfaceName.equals("org.eclipse.persistence.indirection.ValueHolderInterface"); //$NON-NLS-1$
	}

	/**
	 * return whether the specified type is one of the container
	 * types allowed by the JPA spec
	 */
	protected boolean typeIsContainer() {
		return this.getJpaContainerDefinition().isContainer();
	}


	// ********** metamodel **********

	public String getMetamodelContainerFieldTypeName() {
		throw new UnsupportedOperationException("parent OrmPersistentAttribute implements this"); //$NON-NLS-1$
	}

	public String getMetamodelContainerFieldMapKeyTypeName() {
		throw new UnsupportedOperationException("parent OrmPersistentAttribute implements this"); //$NON-NLS-1$
	}


	public String getMetamodelTypeName() {
		String typeName = this.getTypeName();
		if (typeName == null) {
			return MetamodelField.DEFAULT_TYPE_NAME;
		}
		if (ClassName.isPrimitive(typeName)) {
			return ClassName.getWrapperClassName(typeName);  // ???
		}
		return typeName;
	}
	public JpaContainerDefinition getJpaContainerDefinition() {
		return this.jpaContainerDefinition;
	}

	protected void updateJpaContainerDefinition() {
		this.setJpaContainerDefinition(this.buildJpaContainerDefinition());
	}

	protected void setJpaContainerDefinition(JpaContainerDefinition jpaContainerDefinition) {
		JpaContainerDefinition old = this.jpaContainerDefinition;
		this.jpaContainerDefinition = jpaContainerDefinition;
		firePropertyChanged(JPA_CONTAINER_DEFINITION, old, this.jpaContainerDefinition);
	}

	//I don't think we should be doing this here, I think OrmAttributeMappings should be responsible for their own JpaContainerDefinition
	//Generic can just get it from the JavaPersistentAttribute
	/**
	 * Return the JPA container definition corresponding to the specified type;
	 * return a "null" definition if the specified type is not "assignable to" one of the
	 * container types allowed by the JPA spec.
	 */
	protected JpaContainerDefinition buildJpaContainerDefinition() {
		String typeName = this.getTypeName();
		if (typeName != null) {
			//performance - loop and check for .equals() first
			for (JpaContainerDefinition definition : this.getJpaContainerDefinitions()) {
				if (definition.getTypeName().equals(typeName)) {
					return definition;
				}
			}
			for (JpaContainerDefinition definition : this.getJpaContainerDefinitions()) {
				if (JDTTools.typeIsSubType(this.getJavaProject(), typeName, definition.getTypeName())) {
					return definition;
				}
			}
		}
		return JpaContainerDefinition.Null.instance();
	}

	protected Iterable<JpaContainerDefinition> getJpaContainerDefinitions() {
		return JPA_CONTAINER_DEFINITIONS;
	}

	protected static final JpaContainerDefinition[] JPA_CONTAINER_DEFINITION_ARRAY = new JpaContainerDefinition[] {
		new CollectionJpaContainerDefinition(java.util.Set.class, JPA2_0.SET_ATTRIBUTE),
		new CollectionJpaContainerDefinition(java.util.List.class, JPA2_0.LIST_ATTRIBUTE),
		new CollectionJpaContainerDefinition(java.util.Collection.class, JPA2_0.COLLECTION_ATTRIBUTE),
		new MapJpaContainerDefinition(java.util.Map.class, JPA2_0.MAP_ATTRIBUTE)
	};

	protected static final Iterable<JpaContainerDefinition> JPA_CONTAINER_DEFINITIONS = new ArrayIterable<JpaContainerDefinition>(JPA_CONTAINER_DEFINITION_ARRAY);


	/**
	 * Abstract JPA container definition
	 */
	protected abstract static class AbstractJpaContainerDefinition
		implements JpaContainerDefinition
	{
		protected final String typeName;
		protected final String metamodelContainerFieldTypeName;

		protected AbstractJpaContainerDefinition(Class<?> containerClass, String metamodelContainerFieldTypeName) {
			this(containerClass.getName(), metamodelContainerFieldTypeName);
		}

		protected AbstractJpaContainerDefinition(String typeName, String metamodelContainerFieldTypeName) {
			super();
			if ((typeName == null) || (metamodelContainerFieldTypeName == null)) {
				throw new NullPointerException();
			}
			this.typeName = typeName;
			this.metamodelContainerFieldTypeName = metamodelContainerFieldTypeName;
		}

		public String getTypeName() {
			return this.typeName;
		}

		public boolean isContainer() {
			return true;
		}

		public String getMetamodelContainerFieldTypeName() {
			return this.metamodelContainerFieldTypeName;
		}

		public String getMultiReferenceTargetTypeName(JavaResourceAttribute resourceAttribute) {
			throw new UnsupportedOperationException();
		}

		public String getMultiReferenceMapKeyTypeName(JavaResourceAttribute resourceAttribute) {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Collection JPA container definition
	 */
	protected static class CollectionJpaContainerDefinition
		extends AbstractJpaContainerDefinition
	{
		protected CollectionJpaContainerDefinition(Class<?> collectionClass, String staticMetamodelTypeDeclarationTypeName) {
			super(collectionClass, staticMetamodelTypeDeclarationTypeName);
		}

		public String getMetamodelContainerFieldMapKeyTypeName(CollectionMapping mapping) {
			return null;
		}

		public boolean isMap() {
			return false;
		}
	}

	/**
	 * Map JPA container definition
	 */
	protected static class MapJpaContainerDefinition
		extends AbstractJpaContainerDefinition
	{
		protected MapJpaContainerDefinition(Class<?> mapClass, String staticMetamodelTypeDeclarationTypeName) {
			super(mapClass, staticMetamodelTypeDeclarationTypeName);
		}

		public String getMetamodelContainerFieldMapKeyTypeName(CollectionMapping mapping) {
			return mapping.getMetamodelFieldMapKeyTypeName();
		}

		public boolean isMap() {
			return true;
		}
	}


	// ********** misc **********

	@Override
	public OrmPersistentType getParent() {
		return (OrmPersistentType) super.getParent();
	}

	public OrmPersistentType getOwningPersistentType() {
		return this.getParent();
	}

	public TypeMapping getOwningTypeMapping() {
		return this.getOwningPersistentType().getMapping();
	}

	protected EntityMappings getEntityMappings() {
		return (EntityMappings) getParent().getMappingFileRoot();
	}

	public String getPrimaryKeyColumnName() {
		throw new UnsupportedOperationException("Owing orm persistent attribute should handle, this should not be called."); //$NON-NLS-1$
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}
}
