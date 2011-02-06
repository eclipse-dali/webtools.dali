/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.MethodSignature;
import org.eclipse.jpt.common.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.common.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmStructureNodes;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceNode;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaPersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmReadOnlyPersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.resource.java.Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.core.resource.java.NestableAnnotation;

/**
 * <em>virtual</em> <code>orm.xml</code> persistent attribute
 */
public class VirtualOrmPersistentAttribute
	extends AbstractOrmXmlContextNode
	implements OrmReadOnlyPersistentAttribute2_0
{
	protected final JavaResourcePersistentAttribute javaResourceAttribute;

	/**
	 * This is an "annotated" Java persistent attribute whose state is
	 * determined by its annotations (just like a "normal" Java attribute).
	 * Its parent is an <code>orm.xml</code> persistent type. This is necessary
	 * because the Java attribute's context is the <code>orm.xml</code> type
	 * (e.g. the Java attribute's default table is the table set in the
	 * <code>orm.xml</code> type, not the Java type).
	 */
	protected final JavaPersistentAttribute annotatedJavaAttribute;

	/**
	 * This is the "original" Java persistent attribute corresponding to
	 * {@link #javaResourceAttribute} from the Java context
	 * model. If it is found (it can be <code>null</code> if the
	 * <code>orm.xml</code> access type differs from the Java's), we need to
	 * listen to it for changes so we can
	 * refresh our "local" Java attributes (since the Java resource model does
	 * not fire change events, and trigger a <em>sync</em>, when it is modified
	 * by the Java context model - if there is no Java context attribute, the
	 * Java resource model can only be modified via source code editing).
	 */
	protected JavaPersistentAttribute originalJavaAttribute;
	protected StateChangeListener originalJavaAttributeListener;

	/**
	 * This is a simulated "unannotated" Java persistent attribute. It is built
	 * only if necessary (i.e. when the <code>orm.xml</code> persistent type
	 * has been tagged <em>metadata complete</em>). Like
	 * {@link #annotatedJavaAttribute}, its parent is an
	 * <code>orm.xml</code> persistent type.
	 */
	protected JavaPersistentAttribute unannotatedJavaAttribute;

	protected JavaAttributeMapping mapping;  // never null


	public VirtualOrmPersistentAttribute(OrmPersistentType parent, JavaResourcePersistentAttribute javaResourceAttribute) {
		super(parent);
		this.javaResourceAttribute = javaResourceAttribute;
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
		return (javaType == null) ? null : javaType.getAttributeFor(this.javaResourceAttribute);
	}

	protected JavaPersistentAttribute2_0 getJavaPersistentAttribute2_0() {
		return (JavaPersistentAttribute2_0) this.getJavaPersistentAttribute();
	}

	protected JavaPersistentAttribute buildAnnotatedJavaAttribute() {
		return this.buildJavaAttribute(this.javaResourceAttribute);
	}

	protected JavaPersistentAttribute getUnannotatedJavaAttribute() {
		if (this.unannotatedJavaAttribute == null) {
			this.unannotatedJavaAttribute = this.buildUnannotatedJavaAttribute();
		}
		return this.unannotatedJavaAttribute;
	}

	protected JavaPersistentAttribute buildUnannotatedJavaAttribute() {
		return this.buildJavaAttribute(this.buildUnannotatedJavaResourceAttribute());
	}

	/**
	 * Build a Java resource attribute that wraps the original Java resource
	 * attribute and behaves as though it has no annotations. This will cause
	 * all the settings in the Java <em>context</em> attribute to default.
	 */
	protected JavaResourcePersistentAttribute buildUnannotatedJavaResourceAttribute() {
		return new UnannotatedJavaResourcePersistentAttribute(this.javaResourceAttribute);
	}

	protected JavaPersistentAttribute buildJavaAttribute(JavaResourcePersistentAttribute jrpa) {
		// pass in the orm persistent type as the parent...
		return this.getJpaFactory().buildJavaPersistentAttribute(this.getOwningPersistentType(), jrpa);
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

	public JavaResourcePersistentAttribute getJavaResourcePersistentAttribute() {
		return this.javaResourceAttribute;
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


	// ********** specified/virtual **********

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


	// ********** unannotated Java resource persistent member **********

	/**
	 * Wrap another Java resource member and suppress all its annotations.
	 */
	protected abstract class UnannotatedJavaResourcePersistentMember<M extends JavaResourcePersistentMember>
		extends SourceNode
		implements JavaResourcePersistentMember
	{
		protected final M member;

		/**
		 * these are built as needed
		 */
		protected final HashMap<String, Annotation> nullAnnotationsCache = new HashMap<String, Annotation>();


		protected UnannotatedJavaResourcePersistentMember(M member) {
			super(member.getParent());
			this.member = member;
		}

		public void initialize(CompilationUnit astRoot) {
			// NOP
		}

		public void synchronizeWith(CompilationUnit astRoot) {
			// NOP
		}


		// ********** annotations **********

		public Iterator<Annotation> annotations() {
			return EmptyIterator.instance();
		}

		public int annotationsSize() {
			return 0;
		}

		public Annotation getAnnotation(String annotationName) {
			return null;
		}

		public synchronized Annotation getNonNullAnnotation(String annotationName) {
			Annotation annotation = this.nullAnnotationsCache.get(annotationName);
			if (annotation == null) {
				annotation = this.buildNullAnnotation(annotationName);
				this.nullAnnotationsCache.put(annotationName, annotation);
			}
			return annotation;
		}

		protected abstract Annotation buildNullAnnotation(String annotationName);

		public Iterator<NestableAnnotation> annotations(String nestableAnnotationName, String containerAnnotationName) {
			return EmptyIterator.instance();
		}

		public Annotation addAnnotation(String annotationName) {
			throw new UnsupportedOperationException();
		}

		public NestableAnnotation addAnnotation(int index, String nestableAnnotationName, String containerAnnotationName) {
			throw new UnsupportedOperationException();
		}

		public void moveAnnotation(int targetIndex, int sourceIndex, String containerAnnotationName) {
			throw new UnsupportedOperationException();
		}

		public void removeAnnotation(String annotationName) {
			throw new UnsupportedOperationException();
		}

		public void removeAnnotation(int index, String nestableAnnotationName, String containerAnnotationName) {
			throw new UnsupportedOperationException();
		}

		public Annotation setPrimaryAnnotation(String primaryAnnotationName, Iterable<String> supportingAnnotationNames) {
			throw new UnsupportedOperationException();
		}

		public void addStandAloneAnnotation(NestableAnnotation standAloneAnnotation) {
			throw new UnsupportedOperationException();
		}


		// ********** persistable **********

		public boolean isPersistable() {
			return this.member.isPersistable();
		}


		// ********** misc **********

		public boolean isAnnotated() {
			return false;
		}

		public boolean isFinal() {
			return this.member.isFinal();
		}

		public boolean isFor(String memberName, int occurrence) {
			return this.member.isFor(memberName, occurrence);
		}

		public TextRange getTextRange(CompilationUnit astRoot) {
			return this.member.getTextRange(astRoot);
		}

		public TextRange getNameTextRange(CompilationUnit astRoot) {
			return this.member.getNameTextRange(astRoot);
		}

		public void resolveTypes(CompilationUnit astRoot) {
			// NOP
		}
	}


	// ********** unannotated Java resource persistent member **********

	protected class UnannotatedJavaResourcePersistentAttribute
		extends UnannotatedJavaResourcePersistentMember<JavaResourcePersistentAttribute>
		implements JavaResourcePersistentAttribute
	{
		protected UnannotatedJavaResourcePersistentAttribute(JavaResourcePersistentAttribute attribute){
			super(attribute);
		}


		// ********** annotations **********

		@Override
		public Annotation buildNullAnnotation(String annotationName) {
			return (annotationName == null) ? null : this.buildNullAnnotation_(annotationName);
		}

		private Annotation buildNullAnnotation_(String annotationName) {
			return this.getAnnotationProvider().buildNullAttributeAnnotation(this, annotationName);
		}


		// ********** delegated behavior **********

		@Override
		public JavaResourcePersistentType getParent() {
			return this.member.getParent();
		}

		public JavaResourcePersistentType getResourcePersistentType() {
			return this.member.getResourcePersistentType();
		}

		public String getName() {
			return this.member.getName();
		}

		public boolean isFor(MethodSignature signature, int occurrence) {
			return this.member.isFor(signature, occurrence);
		}

		public boolean isField() {
			return this.member.isField();
		}

		public boolean isProperty() {
			return this.member.isProperty();
		}

		public org.eclipse.jpt.jpa.core.resource.java.AccessType getSpecifiedAccess() {
			return null;
		}

		public boolean typeIsSubTypeOf(String tn) {
			return this.member.typeIsSubTypeOf(tn);
		}

		public boolean typeIsVariablePrimitive() {
			return this.member.typeIsVariablePrimitive();
		}

		public int getModifiers() {
			return this.member.getModifiers();
		}

		public String getTypeName() {
			return this.member.getTypeName();
		}

		public boolean typeIsInterface() {
			return this.member.typeIsInterface();
		}

		public boolean typeIsEnum() {
			return this.member.typeIsEnum();
		}

		public ListIterator<String> typeSuperclassNames() {
			return this.member.typeSuperclassNames();
		}

		public Iterator<String> typeInterfaceNames() {
			return this.member.typeInterfaceNames();
		}

		public ListIterator<String> typeTypeArgumentNames() {
			return this.member.typeTypeArgumentNames();
		}

		public int typeTypeArgumentNamesSize() {
			return this.member.typeTypeArgumentNamesSize();
		}

		public String getTypeTypeArgumentName(int index) {
			return this.member.getTypeTypeArgumentName(index);
		}

		@Override
		public void toString(StringBuilder sb) {
			sb.append(this.getName());
		}
	}
}
