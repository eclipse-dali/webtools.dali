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
import java.util.List;
import java.util.Map;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.BodySourceWriter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.TypeBinding;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaNullTypeMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelSourceType2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.PersistentType2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmPersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeMapping;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkVirtualJavaPersistentType
		extends AbstractJavaContextModel<EclipseLinkOrmPersistentType>
		implements JavaPersistentType, PersistentType2_0 {
	
	private final XmlTypeMapping xmlTypeMapping;

	protected final JavaTypeMapping mapping;
	

	public EclipseLinkVirtualJavaPersistentType(EclipseLinkOrmPersistentType parent, XmlTypeMapping xmlTypeMapping) {
		super(parent);
		this.xmlTypeMapping = xmlTypeMapping;
		this.mapping = new JavaNullTypeMapping(this);
	}

	protected EclipseLinkEntityMappings getEntityMappings() {
		return (EclipseLinkEntityMappings) this.parent.getMappingFileRoot();
	}


	// ********** synchronize/update **********
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
	}

	public void addRootStructureNodesTo(JpaFile jpaFile, Collection<JpaStructureNode> rootStructureNodes) {
		// NOP - virtual types do not have a corresponding Java type
	}


	// ********** name **********

	//The parent OrmPersistentType builds its name from the specified class and package.
	//In SpecifiedOrmPersistentType.updateJavaPersistentType(), it compares the names and
	//rebuilds if the name has changed. We don't need to rebuild the virtual java persistent
	//type based on a name change, it will get rebuilt if the dynamic state changes.
	public String getName() {
		return this.parent.getName();
	}

	public String getSimpleName() {
		return this.parent.getSimpleName();
	}

	public String getTypeQualifiedName() {
		return this.parent.getTypeQualifiedName();
	}


	// ********** access **********

	public AccessType getSpecifiedAccess() {
		return null;
	}

	public void setSpecifiedAccess(AccessType newSpecifiedAccess) {
		throw new UnsupportedOperationException();
	}

	public AccessType getDefaultAccess() {
		return null;
	}

	public AccessType getAccess() {
		return null;
	}


	// ********** mapping **********

	public JavaTypeMapping getMapping() {
		return this.mapping;
	}

	public String getMappingKey() {
		return this.mapping.getKey();
	}

	public void setMappingKey(String key) {
		throw new UnsupportedOperationException();
	}

	public boolean isMapped() {
		return false;
	}


	// ***** super persistent type *****
	
	public PersistentType getSuperPersistentType() {
		TypeMapping superTypeMapping = this.mapping.getSuperTypeMapping();
		return (superTypeMapping == null) ? null : superTypeMapping.getPersistentType();
	}
	
	public Iterable<PersistentType> getAncestors() {
		return IterableTools.transform(getMapping().getAncestors(), TypeMapping.PERSISTENT_TYPE_TRANSFORMER);
	}
	
	public Iterable<PersistentType> getInheritanceHierarchy() {
		return IterableTools.transform(getMapping().getInheritanceHierarchy(), TypeMapping.PERSISTENT_TYPE_TRANSFORMER);
	}
	
	
	// ********** attributes **********
	//The VirtualJavaPersistentAttributes are built by the OrmEclipseLinkPersistentAttribute, no attributes here

	public ListIterable<JavaSpecifiedPersistentAttribute> getAttributes() {
		return EmptyListIterable.instance();
	}

	public JavaSpecifiedPersistentAttribute getAttributeNamed(String attributeName) {
		return null;
	}

	public boolean hasAnyAnnotatedAttributes() {
		return false;
	}

	public JavaSpecifiedPersistentAttribute getAttributeFor(JavaResourceAttribute javaResourceAttribute) {
		return null;
	}

	public int getAttributesSize() {
		return 0;
	}

	public Iterable<String> getAttributeNames() {
		return EmptyIterable.instance();
	}

	public Iterable<PersistentAttribute> getAllAttributes() {
		return EmptyIterable.instance();
	}

	public Iterable<String> getAllAttributeNames() {
		return EmptyIterable.instance();
	}

	public PersistentAttribute resolveAttribute(String attributeName) {
		return null;
	}

	public TypeBinding getAttributeTypeBinding(PersistentAttribute attribute) {
		return null;
	}

	public void attributeChanged(PersistentAttribute attribute) {
		// NOP
	}


	// ********** JpaStructureNode implementation **********

	public ContextType getContextType() {
		throw new UnsupportedOperationException();
	}

	public Class<PersistentType> getManagedTypeType() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Virtual Java types will never show up in the JPA Structure view
	 * or the JPA Details view because there is no corresponding source file
	 * to be displayed in the editor.
	 */
	public Class<JavaPersistentType> getStructureType() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see #getStructureType()
	 */
	public ListIterable<JpaStructureNode> getStructureChildren() {
		throw new UnsupportedOperationException();
	}

	public int getStructureChildrenSize() {
		throw new UnsupportedOperationException();
	}

	public TextRange getFullTextRange() {
		throw new UnsupportedOperationException();
	}

	public boolean containsOffset(int textOffset) {
		throw new UnsupportedOperationException();
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		throw new UnsupportedOperationException();
	}

	public TextRange getSelectionTextRange() {
		throw new UnsupportedOperationException();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		throw new UnsupportedOperationException();
	}

	public TextRange getValidationTextRange() {
		throw new UnsupportedOperationException();
	}


	// ********** misc **********

	public JavaResourceType getJavaResourceType() {
		return null;
	}

	public boolean isFor(String typeName) {
		throw new UnsupportedOperationException();
	}

	public boolean isIn(IPackageFragment packageFragment) {
		throw new UnsupportedOperationException();
	}

	public PersistentType getOverriddenPersistentType() {
		throw new UnsupportedOperationException();
	}

	public String getDeclaringTypeName() {
		String className = this.xmlTypeMapping.getClassName();
		if (className == null) {
			return null;
		}
		int index = className.lastIndexOf('$');
		return (index == -1) ? null : className.substring(0, index).replace('$', '.');
	}

	public boolean isManaged() {
		throw new UnsupportedOperationException();
	}

	public IFile getMetamodelFile() {
		throw new UnsupportedOperationException();
	}

	public void synchronizeMetamodel(Map<String, Collection<MetamodelSourceType2_0>> memberTypeTree) {
		throw new UnsupportedOperationException();
	}

	public void printBodySourceOn(BodySourceWriter pw, Map<String, Collection<MetamodelSourceType2_0>> memberTypeTree) {
		throw new UnsupportedOperationException();
	}

	public IJavaElement getJavaElement() {
		return null;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}


	// ********** metamodel **********

	public PersistentType2_0 getMetamodelType() {
		return this;
	}
}
