/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.HashMap;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceNode;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.Accessor;

public abstract class AbstractAccessor 
	extends AbstractJavaJpaContextNode 
	implements Accessor
{
	
	protected AbstractAccessor(ReadOnlyPersistentAttribute parent) {
		super(parent);
	}


	// ********** unannotated Java resource member **********

	/**
	 * Wrap another Java resource member and suppress all its annotations.
	 */
	protected abstract class UnannotatedJavaResourceMember<M extends JavaResourceMember>
		extends SourceNode
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

		public void initialize(CompilationUnit astRoot) {
			// NOP
		}

		public void synchronizeWith(CompilationUnit astRoot) {
			// NOP
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

		public boolean isAnnotated() {
			return false;
		}

		public boolean isAnnotatedWith(Iterable<String> annotationNames) {
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

		public boolean isFor(String memberName, int occurrence) {
			return this.member.isFor(memberName, occurrence);
		}

		public TextRange getTextRange(CompilationUnit astRoot) {
			// should never be null
			return this.member.getTextRange(astRoot);
		}

		public TextRange getNameTextRange(CompilationUnit astRoot) {
			// should never be null
			return this.member.getNameTextRange(astRoot);
		}

		public void resolveTypes(CompilationUnit astRoot) {
			// NOP
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

		public boolean typeIsArray() {
			return this.member.typeIsArray();
		}

		public ListIterable<String> getTypeSuperclassNames() {
			return this.member.getTypeSuperclassNames();
		}

		public Iterable<String> getTypeInterfaceNames() {
			return this.member.getTypeInterfaceNames();
		}

		public ListIterable<String> getTypeTypeArgumentNames() {
			return this.member.getTypeTypeArgumentNames();
		}

		public int getTypeTypeArgumentNamesSize() {
			return this.member.getTypeTypeArgumentNamesSize();
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
