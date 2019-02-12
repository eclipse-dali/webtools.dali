/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.HashMap;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceModel;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.TypeBinding;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.Accessor;

public abstract class AbstractAccessor
	extends AbstractJavaContextModel<PersistentAttribute>
	implements Accessor
{

	protected AbstractAccessor(PersistentAttribute parent) {
		super(parent);
	}

	public PersistentAttribute getAttribute() {
		return this.parent;
	}

	public TextRange getValidationTextRange() {
		return this.getResourceAttribute().getNameTextRange();
	}


	// ********** unannotated Java resource member **********

	/**
	 * Wrap another Java resource member and suppress all its annotations.
	 */
	protected abstract class UnannotatedJavaResourceMember<M extends JavaResourceMember>
		extends SourceModel
		implements JavaResourceMember
	{
		protected final M member;

		/**
		 * these are built as needed
		 */
		protected final HashMap<String, Annotation> nullAnnotationsCache = new HashMap<String, Annotation>();


		protected UnannotatedJavaResourceMember(M member) {
			super(member.getParent());
			this.member = member;
		}


		// ********** annotations **********

		public Iterable<Annotation> getAnnotations() {
			return EmptyIterable.instance();
		}

		public int getAnnotationsSize() {
			return 0;
		}

		public Annotation getAnnotation(String annotationName) {
			return null;
		}

		public Annotation getContainerAnnotation(String containerAnnotationName) {
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

		public ListIterable<NestableAnnotation> getAnnotations(String nestableAnnotationName) {
			return EmptyListIterable.instance();
		}

		public int getAnnotationsSize(String nestableAnnotationName) {
			return 0;
		}

		public NestableAnnotation getAnnotation(int index, String nestableAnnotationName) {
			return null;
		}

		public Annotation addAnnotation(String annotationName) {
			throw new UnsupportedOperationException();
		}

		public NestableAnnotation addAnnotation(int index, String nestableAnnotationName) {
			throw new UnsupportedOperationException();
		}

		public void moveAnnotation(int targetIndex, int sourceIndex, String containerAnnotationName) {
			throw new UnsupportedOperationException();
		}

		public void removeAnnotation(String annotationName) {
			throw new UnsupportedOperationException();
		}

		public void removeAnnotation(int index, String nestableAnnotationName) {
			throw new UnsupportedOperationException();
		}

		public Annotation setPrimaryAnnotation(String primaryAnnotationName, Iterable<String> supportingAnnotationNames) {
			throw new UnsupportedOperationException();
		}


		// ********** misc **********

		public Iterable<Annotation> getTopLevelAnnotations() {
			return EmptyIterable.instance();
		}

		public boolean isAnnotated() {
			return false;
		}

		public boolean isAnnotatedWithAnyOf(Iterable<String> annotationNames) {
			return false;
		}

		public boolean isFinal() {
			return this.member.isFinal();
		}

		public boolean isPublic() {
			return this.member.isPublic();
		}

		public boolean isStatic() {
			return this.member.isStatic();
		}

		public boolean isTransient() {
			return this.member.isTransient();
		}

		public boolean isProtected() {
			return this.member.isProtected();
		}

		public boolean isPublicOrProtected() {
			return this.isPublic() || this.isProtected();
		}

		public boolean isFor(String memberName, int occurrence) {
			return this.member.isFor(memberName, occurrence);
		}

		public TextRange getTextRange() {
			// should never be null
			return this.member.getTextRange();
		}

		public TextRange getNameTextRange() {
			// should never be null
			return this.member.getNameTextRange();
		}

		public TextRange getTextRange(String nestableAnnotationName) {
			// should never be null
			return this.member.getTextRange(nestableAnnotationName);
		}
	}

	// ********** unannotated Java resource attribute **********

	/**
	 * Wrap another Java resource attribute and suppress all its annotations.
	 */
	protected abstract class UnannotatedJavaResourceAttribute<A extends JavaResourceAttribute>
		extends UnannotatedJavaResourceMember<A>
		implements JavaResourceAttribute
	{
		public UnannotatedJavaResourceAttribute(A attribute){
			super(attribute);
		}

		// ********** annotations **********

		@Override
		public Annotation buildNullAnnotation(String annotationName) {
			return (annotationName == null) ? null : this.buildNullAnnotation_(annotationName);
		}

		private Annotation buildNullAnnotation_(String annotationName) {
			return this.getAnnotationProvider().buildNullAnnotation(this, annotationName);
		}


		// ********** delegated behavior **********

		@Override
		public JavaResourceType getParent() {
			return this.member.getParent();
		}

		public JavaResourceType getResourceType() {
			return this.member.getResourceType();
		}

		public String getName() {
			return this.member.getName();
		}

		public int getModifiers() {
			return this.member.getModifiers();
		}

		public TypeBinding getTypeBinding() {
			return this.member.getTypeBinding();
		}

		@Override
		public void toString(StringBuilder sb) {
			sb.append(this.getName());
		}
	}
}
