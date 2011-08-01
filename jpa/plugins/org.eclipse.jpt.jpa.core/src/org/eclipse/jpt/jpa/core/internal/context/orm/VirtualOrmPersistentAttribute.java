/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.java.Accessor;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmStructureNodes;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.java.FieldAccessor;
import org.eclipse.jpt.jpa.core.internal.context.java.PropertyAccessor;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaPersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmReadOnlyPersistentAttribute2_0;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <em>virtual</em> <code>orm.xml</code> persistent attribute
 */
public class VirtualOrmPersistentAttribute
	extends AbstractOrmXmlContextNode
	implements OrmReadOnlyPersistentAttribute2_0
{
	protected final Accessor javaAccessor;

	/**
	 * This is an "annotated" Java persistent attribute whose state is
	 * determined by its annotations (just like a "normal" Java attribute).
	 * Its parent is an <code>orm.xml</code> persistent type. This is necessary
	 * because the Java attribute's <em>context</em> is the <code>orm.xml</code>
	 * type (e.g. the Java attribute's default table is the table set in the
	 * <code>orm.xml</code> type, not the Java type).
	 * The {@link #originalJavaAttributeListener} keeps this attribute in sync
	 * with any changes made via the Java context model.
	 */
	protected final JavaPersistentAttribute annotatedJavaAttribute;

	/**
	 * This is the "original" Java persistent attribute corresponding to
	 * {@link #javaResourceAttribute} from the Java context model.
	 * If it is found (it can be <code>null</code> if the <code>orm.xml</code>
	 * access type differs from the Java access type), we need to listen to it
	 * for changes so we can refresh our "local" Java attributes (since the
	 * Java resource model does not fire change events, and trigger a
	 * <em>sync</em>, when it is modified by the Java context model - if there
	 * is no Java context attribute, the Java resource model can only be
	 * modified via source code editing and we will <em>sync</em> appropriately).
	 */
	protected JavaPersistentAttribute originalJavaAttribute;
	protected StateChangeListener originalJavaAttributeListener;

	/**
	 * This is a simulated "unannotated" Java persistent attribute. It is built
	 * only if necessary (i.e. when the <code>orm.xml</code> persistent type
	 * has been tagged <em>metadata complete</em>). Like
	 * {@link #annotatedJavaAttribute}, its parent is an
	 * <code>orm.xml</code> persistent type.
	 * The {@link #originalJavaAttributeListener} keeps this attribute in sync
	 * with any changes made via the Java context model.
	 */
	protected JavaPersistentAttribute unannotatedJavaAttribute;

	protected JavaAttributeMapping mapping;  // never null


	public VirtualOrmPersistentAttribute(OrmPersistentType parent, JavaResourceField resourceField) {
		super(parent);
		this.javaAccessor = new FieldAccessor(this, resourceField);
		this.annotatedJavaAttribute = this.buildAnnotatedJavaAttribute();
		this.mapping = this.buildMapping();
	}

	public VirtualOrmPersistentAttribute(OrmPersistentType parent, JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
		super(parent);
		this.javaAccessor = new PropertyAccessor(this, resourceGetter, resourceSetter);
		this.annotatedJavaAttribute = this.buildAnnotatedJavaAttribute();
		this.mapping = this.buildMapping();
	}

	public VirtualOrmPersistentAttribute(OrmPersistentType parent, Accessor javaAccessor) {
		super(parent);
		this.javaAccessor = javaAccessor;
		this.annotatedJavaAttribute = this.buildAnnotatedJavaAttribute();
		this.mapping = this.buildMapping();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncLocalJavaAttributes();
		// 'mapping' belongs to one of the "local" Java persistent attributes
	}

	@Override
	public void update() {
		super.update();
		this.updateOriginalJavaAttribute();
		this.updateLocalJavaAttributes();
		this.setMapping(this.buildMapping());
	}


	// ********** mapping **********

	public JavaAttributeMapping getMapping() {
		return this.mapping;
	}

	protected void setMapping(JavaAttributeMapping mapping) {
		JavaAttributeMapping old = this.mapping;
		this.mapping = mapping;
		this.firePropertyChanged(DEFAULT_MAPPING_KEY_PROPERTY, old, mapping);
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

	public JavaPersistentAttribute getJavaPersistentAttribute() {
		return this.getOwningTypeMapping().isMetadataComplete() ?
				this.getUnannotatedJavaAttribute() :
				this.annotatedJavaAttribute;
	}

	public JavaPersistentAttribute resolveJavaPersistentAttribute() {
		JavaPersistentType javaType = this.getOwningPersistentType().getJavaPersistentType();
		return (javaType == null) ? null : javaType.getAttributeFor(this.getJavaResourceAttribute());
	}

	protected JavaPersistentAttribute2_0 getJavaPersistentAttribute2_0() {
		return (JavaPersistentAttribute2_0) this.getJavaPersistentAttribute();
	}

	protected JavaPersistentAttribute buildAnnotatedJavaAttribute() {
		return buildJavaAttribute(this.javaAccessor);
	}

	protected JavaPersistentAttribute getUnannotatedJavaAttribute() {
		if (this.unannotatedJavaAttribute == null) {
			this.unannotatedJavaAttribute = this.buildUnannotatedJavaAttribute();
		}
		return this.unannotatedJavaAttribute;
	}

	protected JavaPersistentAttribute buildUnannotatedJavaAttribute() {
		// pass in the orm persistent type as the parent...
		return this.javaAccessor.buildUnannotatedJavaAttribute(this.getOwningPersistentType());
	}

	protected JavaPersistentAttribute buildJavaAttribute(Accessor accessor) {
		// pass in the orm persistent type as the parent...
		return this.getJpaFactory().buildJavaPersistentAttribute(this.getOwningPersistentType(), accessor);
	}

	protected void syncLocalJavaAttributes() {
		this.annotatedJavaAttribute.synchronizeWithResourceModel();
		if (this.unannotatedJavaAttribute != null) {
			this.unannotatedJavaAttribute.synchronizeWithResourceModel();
		}
	}

	protected void updateLocalJavaAttributes() {
		this.annotatedJavaAttribute.update();
		if (this.unannotatedJavaAttribute != null) {
			this.unannotatedJavaAttribute.update();
		}
	}

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

	// ********** original Java persistent attribute **********

	protected void updateOriginalJavaAttribute() {
		JavaPersistentAttribute newJavaAttribute = this.resolveJavaPersistentAttribute();
		if (newJavaAttribute != this.originalJavaAttribute) {
			if (newJavaAttribute == null) {
				this.originalJavaAttribute.removeStateChangeListener(this.getOriginalJavaAttributeListener());
				this.originalJavaAttribute = null;
			} else {
				if (this.originalJavaAttribute != null) {
					this.originalJavaAttribute.removeStateChangeListener(this.getOriginalJavaAttributeListener());
				}
				this.originalJavaAttribute = newJavaAttribute;
				this.originalJavaAttribute.addStateChangeListener(this.getOriginalJavaAttributeListener());
			}
		}
	}

	protected StateChangeListener getOriginalJavaAttributeListener() {
		if (this.originalJavaAttributeListener == null) {
			this.originalJavaAttributeListener = this.buildOriginalJavaAttributeListener();
		}
		return this.originalJavaAttributeListener;
	}

	protected StateChangeListener buildOriginalJavaAttributeListener() {
		return new StateChangeListener() {
			public void stateChanged(StateChangeEvent event) {
				VirtualOrmPersistentAttribute.this.originalJavaAttributeChanged();
			}
		};
	}

	/**
	 * If the "original" Java persistent attribute (or any of its parts) changes
	 * we need to sync our "local" Java persistent attributes with any possible
	 * changes to the Java resource model. This is necessary for when the Java
	 * context model is modifying the Java resource model, but is redundant when
	 * the Java resource model is triggering a <em>sync</em>.
	 */
	protected void originalJavaAttributeChanged() {
		this.syncLocalJavaAttributes();
	}


	// ********** access **********

	public AccessType getAccess() {
		return this.getJavaPersistentAttribute().getAccess();
	}


	// ********** specified/default **********

	public boolean isVirtual() {
		return true;
	}

	public OrmReadOnlyPersistentAttribute convertToVirtual() {
		throw new UnsupportedOperationException();
	}

	public OrmPersistentAttribute convertToSpecified() {
		if (this.mapping.getKey() == null) {
			throw new IllegalStateException("Use convertToSpecified(String) instead and specify a mapping type"); //$NON-NLS-1$
		}
		return this.getOwningPersistentType().convertAttributeToSpecified(this);
	}

	public OrmPersistentAttribute convertToSpecified(String mappingKey) {
		return this.getOwningPersistentType().convertAttributeToSpecified(this, mappingKey);
	}


	// ********** JpaStructureNode implementation **********

	public String getId() {
		return OrmStructureNodes.PERSISTENT_ATTRIBUTE_ID;
	}

	public JpaStructureNode getStructureNode(int offset) {
		return this;
	}

	public boolean contains(int textOffset) {
		return false;
	}

	public TextRange getSelectionTextRange() {
		return null;
	}

	public void dispose() {
		if (this.originalJavaAttribute != null) {
			this.originalJavaAttribute.removeStateChangeListener(this.getOriginalJavaAttributeListener());
		}
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// the Java attribute should not need an AST for validation from here
		this.getJavaPersistentAttribute().validate(messages, reporter, null);
	}

	public TextRange getValidationTextRange() {
		return this.getOwningTypeMapping().getAttributesTextRange();
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

	@Override
	public OrmPersistentType getParent() {
		return (OrmPersistentType) super.getParent();
	}

	public OrmPersistentType getOwningPersistentType() {
		return this.getParent();
	}

	public OrmTypeMapping getOwningTypeMapping() {
		return this.getOwningPersistentType().getMapping();
	}

	public String getPrimaryKeyColumnName() {
		return this.mapping.getPrimaryKeyColumnName();
	}

	public String getTypeName() {
		return this.getJavaPersistentAttribute().getTypeName();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}
}
