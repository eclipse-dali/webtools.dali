/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.Collection;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.TypeBinding;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.Accessor;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.java.FieldAccessor;
import org.eclipse.jpt.jpa.core.internal.context.java.PropertyAccessor;
import org.eclipse.jpt.jpa.core.jpa2.context.PersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedPersistentAttribute2_0;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <em>virtual</em> <code>orm.xml</code> persistent attribute
 */
public class VirtualOrmPersistentAttribute
	extends AbstractOrmXmlContextModel<OrmPersistentType>
	implements OrmPersistentAttribute, PersistentAttribute2_0
{
	protected final Accessor javaAccessor; // never null

	/**
	 * This is an "annotated" Java persistent attribute whose state is
	 * determined by its annotations (just like a "normal" Java attribute).
	 * Its parent is an <code>orm.xml</code> persistent type. This is necessary
	 * because the Java attribute's <em>context</em> is the <code>orm.xml</code>
	 * type (e.g. the Java attribute's default table is the table set in the
	 * <code>orm.xml</code> type, not the Java type).
	 * We can keep this attribute in sync because we are notified of
	 * any changes made via the Java context model
	 * (see {@link org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaPersistentAttribute#stateChanged()}).
	 */
	protected final JavaSpecifiedPersistentAttribute annotatedJavaAttribute;

	/**
	 * This is the "original" Java persistent attribute corresponding to
	 * {@link #javaAccessor} from the Java context model.
	 * (It can be <code>null</code> if the <code>orm.xml</code>
	 * access type differs from the Java access type.)
	 * We are notified when it changes
	 * (see {@link org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaPersistentAttribute#stateChanged()})
	 * so we can refresh our "local" Java attributes (since the
	 * Java resource model does not fire change events, and trigger a
	 * <em>sync</em>, when it is modified by the Java context model - if there
	 * is no Java context attribute, the Java resource model can only be
	 * modified via source code editing and we will <em>sync</em> appropriately).
	 */
	protected JavaSpecifiedPersistentAttribute originalJavaAttribute;

	/**
	 * This is a simulated "unannotated" Java persistent attribute. It is built
	 * only if necessary (i.e. when the <code>orm.xml</code> persistent type
	 * has been tagged <em>metadata complete</em>). Like
	 * {@link #annotatedJavaAttribute}, its parent is an
	 * <code>orm.xml</code> persistent type and we are notified of any
	 * changes made via the Java context model.
	 */
	protected JavaSpecifiedPersistentAttribute unannotatedJavaAttribute;

	protected JavaAttributeMapping mapping;  // never null


	public VirtualOrmPersistentAttribute(OrmPersistentType parent, JavaResourceField resourceField) {
		super(parent);
		this.javaAccessor = this.buildJavaAccessor(resourceField);
		this.annotatedJavaAttribute = this.buildAnnotatedJavaAttribute();
		this.mapping = this.buildMapping();
	}

	public VirtualOrmPersistentAttribute(OrmPersistentType parent, JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
		super(parent);
		this.javaAccessor = this.buildJavaAccessor(resourceGetter, resourceSetter);
		this.annotatedJavaAttribute = this.buildAnnotatedJavaAttribute();
		this.mapping = this.buildMapping();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.syncLocalJavaAttributes(monitor);
		// 'mapping' belongs to one of the "local" Java persistent attributes
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateOriginalJavaAttribute();
		this.updateLocalJavaAttributes(monitor);
		this.setMapping(this.buildMapping());
	}


	// ********** mapping **********

	public JavaAttributeMapping getMapping() {
		return this.mapping;
	}

	protected void setMapping(JavaAttributeMapping mapping) {
		JavaAttributeMapping old = this.mapping;
		this.mapping = mapping;
		this.firePropertyChanged(MAPPING_PROPERTY, old, mapping);
	}

	protected JavaAttributeMapping buildMapping() {
		return this.getJavaPersistentAttribute().getMapping();
	}

	public String getMappingKey() {
		return this.mapping.getKey();
	}

	public String getDefaultMappingKey() {
		return this.mapping.getKey();
	}


	// ********** name **********

	public String getName() {
		return this.mapping.getName();
	}


	// ********** Java persistent attribute **********

	public JavaSpecifiedPersistentAttribute getJavaPersistentAttribute() {
		return this.getDeclaringTypeMapping().isMetadataComplete() ?
				this.getUnannotatedJavaAttribute() :
				this.annotatedJavaAttribute;
	}

	public JavaSpecifiedPersistentAttribute resolveJavaPersistentAttribute() {
		JavaPersistentType javaType = this.getDeclaringPersistentType().getJavaPersistentType();
		return (javaType == null) ? null : javaType.getAttributeFor(this.getJavaResourceAttribute());
	}

	protected SpecifiedPersistentAttribute2_0 getJavaPersistentAttribute2_0() {
		return (SpecifiedPersistentAttribute2_0) this.getJavaPersistentAttribute();
	}

	protected JavaSpecifiedPersistentAttribute buildAnnotatedJavaAttribute() {
		// pass in the orm persistent type as the parent...
		return this.getJpaFactory().buildJavaPersistentAttribute(this.getDeclaringPersistentType(), this.javaAccessor);
	}

	protected JavaSpecifiedPersistentAttribute getUnannotatedJavaAttribute() {
		if (this.unannotatedJavaAttribute == null) {
			this.unannotatedJavaAttribute = this.buildUnannotatedJavaAttribute();
		}
		return this.unannotatedJavaAttribute;
	}

	protected JavaSpecifiedPersistentAttribute buildUnannotatedJavaAttribute() {
		// pass in the orm persistent type as the parent...
		return this.javaAccessor.buildUnannotatedJavaAttribute(this.getDeclaringPersistentType());
	}

	protected void syncLocalJavaAttributes(IProgressMonitor monitor) {
		this.annotatedJavaAttribute.synchronizeWithResourceModel(monitor);
		if (this.unannotatedJavaAttribute != null) {
			this.unannotatedJavaAttribute.synchronizeWithResourceModel(monitor);
		}
	}

	protected void updateLocalJavaAttributes(IProgressMonitor monitor) {
		this.annotatedJavaAttribute.update(monitor);
		if (this.unannotatedJavaAttribute != null) {
			this.unannotatedJavaAttribute.update(monitor);
		}
	}


	// ********** original Java persistent attribute **********

	protected void updateOriginalJavaAttribute() {
		this.originalJavaAttribute = this.resolveJavaPersistentAttribute(); // yes, this is the "original" java attribute
	}

	/**
	 * If the "original" Java persistent attribute (or any of its parts) changes
	 * we need to sync our "local" Java persistent attributes with any possible
	 * changes to the Java resource model. This is necessary for when the Java
	 * context model is modifying the Java resource model, but is redundant when
	 * the Java resource model is triggering a <em>sync</em>.
	 */
	public void javaAttributeChanged(JavaSpecifiedPersistentAttribute attribute) {
		if (this.originalJavaAttribute == attribute) {
			this.syncLocalJavaAttributes(new NullProgressMonitor());
		}
	}


	// ********** Java accessor **********

	public Accessor getJavaAccessor() {
		return this.javaAccessor;
	}

	public JavaResourceAttribute getJavaResourceAttribute() {
		return this.javaAccessor.getResourceAttribute();
	}

	public boolean isFor(JavaResourceField javaResourceField) {
		return this.javaAccessor.isFor(javaResourceField);
	}

	public boolean isFor(JavaResourceMethod javaResourceGetter, JavaResourceMethod javaResourceSetter) {
		return this.javaAccessor.isFor(javaResourceGetter, javaResourceSetter);
	}

	protected Accessor buildJavaAccessor(JavaResourceField resourceField) {
		return new FieldAccessor(this, resourceField);
	}

	protected Accessor buildJavaAccessor(JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
		return new PropertyAccessor(this, resourceGetter, resourceSetter);
	}


	// ********** access **********

	public AccessType getAccess() {
		return this.getJavaPersistentAttribute().getAccess();
	}


	// ********** specified/default **********

	public boolean isVirtual() {
		return true;
	}

	public OrmSpecifiedPersistentAttribute addToXml() {
		if (this.mapping.getKey() == null) {
			throw new IllegalStateException("Use addToXml(String) instead and specify a mapping type"); //$NON-NLS-1$
		}
		return this.getDeclaringPersistentType().addAttributeToXml(this);
	}

	public OrmSpecifiedPersistentAttribute addToXml(String mappingKey) {
		return this.getDeclaringPersistentType().addAttributeToXml(this, mappingKey);
	}


	// ********** JpaStructureNode implementation **********

	public ContextType getContextType() {
		return new ContextType(this);
	}

	public Class<OrmPersistentAttribute> getStructureType() {
		return OrmPersistentAttribute.class;
	}

	public Iterable<JpaStructureNode> getStructureChildren() {
		return IterableTools.emptyIterable();
	}

	public int getStructureChildrenSize() {
		return 0;
	}

	public JpaStructureNode getStructureNode(int offset) {
		return this;
	}

	public TextRange getFullTextRange() {
		return null;
	}

	public boolean containsOffset(int textOffset) {
		return false;
	}

	public TextRange getSelectionTextRange() {
		return null;
	}

	public void addRootStructureNodesTo(JpaFile jpaFile, Collection<JpaStructureNode> rootStructureNodes) {
		throw new UnsupportedOperationException();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.getJavaPersistentAttribute().validate(messages, reporter);
	}

	public TextRange getValidationTextRange() {
		return this.getDeclaringTypeMapping().getAttributesTextRange();
	}


	// ********** metamodel **********

	public String getMetamodelContainerFieldTypeName() {
		return this.getJavaPersistentAttribute2_0().getMetamodelContainerFieldTypeName();
	}

	public String getMetamodelContainerFieldMapKeyTypeName() {
		return this.getJavaPersistentAttribute2_0().getMetamodelContainerFieldMapKeyTypeName();
	}

	public String getMetamodelTypeName() {
		return this.getJavaPersistentAttribute2_0().getMetamodelTypeName();
	}


	// ********** misc **********

	public OrmPersistentType getDeclaringPersistentType() {
		return this.parent;
	}

	public OrmTypeMapping getDeclaringTypeMapping() {
		return this.getDeclaringPersistentType().getMapping();
	}

	public String getPrimaryKeyColumnName() {
		return this.mapping.getPrimaryKeyColumnName();
	}

	public String getTypeName() {
		return this.getJavaPersistentAttribute().getTypeName();
	}
	
	public String getTypeName(PersistentType contextType) {
		while (contextType != null) {
			if (contextType == this.getDeclaringPersistentType()) {
				return this.getTypeName();
			}
			TypeBinding typeBinding = contextType.getAttributeTypeBinding(this);
			if (typeBinding != null) {
				return typeBinding.getQualifiedName();
			}
			contextType = contextType.getSuperPersistentType();
		}
		return null;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}
}
