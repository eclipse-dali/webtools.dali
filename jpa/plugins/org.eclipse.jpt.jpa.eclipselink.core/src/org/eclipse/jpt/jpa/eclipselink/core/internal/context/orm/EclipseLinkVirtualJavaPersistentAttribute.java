/*******************************************************************************
 * Copyright (c) 2012, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.Collection;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.common.core.internal.utility.TypeTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ClassNameTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.CollectionMapping;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.Accessor;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaNullAttributeMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedPersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.internal.plugin.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAttributeMapping;

public class EclipseLinkVirtualJavaPersistentAttribute
	extends AbstractJavaContextModel<OrmPersistentType>
	implements JavaSpecifiedPersistentAttribute, SpecifiedPersistentAttribute2_0, EclipseLinkJavaPersistentAttribute
{
	private final XmlAttributeMapping xmlAttributeMapping;
	
	private final JavaAttributeMapping attributeMapping;

	private JpaContainerDefinition jpaContainerDefinition = JpaContainerDefinition.Null.instance();

	public EclipseLinkVirtualJavaPersistentAttribute(OrmPersistentType parent, XmlAttributeMapping xmlAttributeMapping) {
		super(parent);
		this.xmlAttributeMapping = xmlAttributeMapping;
		this.attributeMapping = new GenericJavaNullAttributeMapping(this);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateJpaContainerDefinition();
	}

	public void addRootStructureNodesTo(JpaFile jpaFile, Collection<JpaStructureNode> rootStructureNodes) {
		throw new UnsupportedOperationException();
	}

	public XmlAttributeMapping getXmlAttributeMapping() {
		return this.xmlAttributeMapping;
	}

	public JavaAttributeMapping getMapping() {
		return this.attributeMapping;
	}

	public JavaAttributeMapping setMappingKey(String key) {
		throw new UnsupportedOperationException();
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

	public String getTypeName() {
		String typeName = this.xmlAttributeMapping.getAttributeType();
		return typeName == null ? null : this.getEntityMappings().qualify(typeName);
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
		throw new UnsupportedOperationException();
	}

	public JavaSpecifiedPersistentAttribute getJavaPersistentAttribute() {
		return this;
	}


	// ********** JpaStructureNode implementation **********

	public TextRange getFullTextRange() {
		throw new UnsupportedOperationException();
	}

	public boolean containsOffset(int offset) {
		throw new UnsupportedOperationException();
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		throw new UnsupportedOperationException();
	}

	public TextRange getSelectionTextRange() {
		throw new UnsupportedOperationException();
	}

	public ContextType getContextType() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Virtual Java attributes will never show up in the JPA Structure view
	 * or the JPA Details view because there is no corresponding source file
	 * to be displayed in the editor.
	 */
	public Class<JavaPersistentAttribute> getStructureType() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see #getStructureType()
	 */
	public Iterable<JpaStructureNode> getStructureChildren() {
		throw new UnsupportedOperationException();
	}

	public int getStructureChildrenSize() {
		throw new UnsupportedOperationException();
	}

	public AccessType getAccess() {
		return null;
	}

	public AccessType getSpecifiedAccess() {
		return null;
	}

	public void setSpecifiedAccess(AccessType newSpecifiedAccess) {
		throw new UnsupportedOperationException();
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
		return TypeTools.isSubType(typeName, DATE_TYPE_NAME, this.getJavaProject())
			|| TypeTools.isSubType(typeName, CALENDAR_TYPE_NAME, this.getJavaProject());
	}

	public boolean typeIsSerializable() {
		String typeName = this.getTypeName();
		if (typeName == null) {
			return false;
		}
		return TypeTools.isSerializable(typeName, this.getJavaProject());
	}

	public boolean typeIsValidForVariableOneToOne() {
		String typeName = this.getTypeName();
		if (typeName == null) {
			return false;
		}
		IType type = JavaProjectTools.findType(getJavaProject(), typeName);
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
		// parent OrmPersistentAttribute implements this
		throw new UnsupportedOperationException();
	}

	public String getMetamodelContainerFieldMapKeyTypeName() {
		// parent OrmPersistentAttribute implements this
		throw new UnsupportedOperationException();
	}


	public String getMetamodelTypeName() {
		String typeName = this.getTypeName();
		if (typeName == null) {
			return MetamodelField2_0.DEFAULT_TYPE_NAME;
		}
		if (ClassNameTools.isPrimitive(typeName)) {
			return ClassNameTools.primitiveWrapperClassName(typeName);  // ???
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
				if (TypeTools.isSubType(typeName, definition.getTypeName(), this.getJavaProject())) {
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

	protected static final Iterable<JpaContainerDefinition> JPA_CONTAINER_DEFINITIONS = IterableTools.iterable(JPA_CONTAINER_DEFINITION_ARRAY);


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

	public OrmPersistentType getDeclaringPersistentType() {
		return this.parent;
	}

	public TypeMapping getDeclaringTypeMapping() {
		return this.getDeclaringPersistentType().getMapping();
	}

	protected EntityMappings getEntityMappings() {
		return (EntityMappings) this.parent.getMappingFileRoot();
	}

	public String getPrimaryKeyColumnName() {
		// parent OrmPersistentAttribute implements this
		throw new UnsupportedOperationException();
	}

	public IJavaElement getJavaElement() {
		return null;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}
}
