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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.MethodSignature;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.PersistentAttributeTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.PersistentPropertyValidator;

public class PropertyAccessor 
	extends AbstractAccessor
{
	private final JavaResourceMethod resourceGetter;

	private final JavaResourceMethod resourceSetter;
	
	public PropertyAccessor(ReadOnlyPersistentAttribute parent, JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
		super(parent);
		this.resourceGetter = resourceGetter;
		this.resourceSetter = resourceSetter;
	}

	public JavaResourceAttribute getResourceAttribute() {
		return this.resourceGetter;
	}

	public boolean isFor(JavaResourceField resourceField) {
		return false;
	}

	public boolean isFor(JavaResourceMethod getterMethod, JavaResourceMethod setterMethod) {
		return (this.resourceGetter == getterMethod) && (this.resourceSetter == setterMethod);
	}

	public AccessType getDefaultAccess() {
		return AccessType.PROPERTY;
	}

	public JavaResourceMethod getResourceGetter() {
		return this.resourceGetter;
	}

	public JavaResourceMethod getResourceSetter() {
		return this.resourceSetter;
	}

	public JavaPersistentAttribute buildUnannotatedJavaAttribute(PersistentType parent) {
		return this.buildJavaAttribute(parent, this.buildUnannotatedJavaResourceGetter(), this.buildUnannotatedJavaResourceSetter());
	}

	/**
	 * Build a Java resource method that wraps the original Java resource
	 * getter method and behaves as though it has no annotations. This will cause
	 * all the settings in the Java <em>context</em> attribute to default.
	 */
	protected JavaResourceMethod buildUnannotatedJavaResourceGetter() {
		return new UnannotatedJavaResourceMethod(this.getResourceGetter());
	}

	/**
	 * Build a Java resource method that wraps the original Java resource
	 * setter method and behaves as though it has no annotations. This will cause
	 * all the settings in the Java <em>context</em> attribute to default.
	 */
	protected JavaResourceMethod buildUnannotatedJavaResourceSetter() {
		return new UnannotatedJavaResourceMethod(this.getResourceSetter());
	}

	protected JavaPersistentAttribute buildJavaAttribute(PersistentType parent, JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
		return this.getJpaFactory().buildJavaPersistentProperty(parent, resourceGetter, resourceSetter);
	}


	public JptValidator buildAttributeValidator(PersistentAttribute persistentAttribute, PersistentAttributeTextRangeResolver textRangeResolver) {
		return new PersistentPropertyValidator(persistentAttribute, this, textRangeResolver);
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getResourceAttribute().getNameTextRange(astRoot);
	}


	// ********** unannotated Java resource method **********

	/**
	 * Wrap another Java resource method and suppress all its annotations.
	 */
	protected class UnannotatedJavaResourceMethod
		extends UnannotatedJavaResourceAttribute<JavaResourceMethod>
		implements JavaResourceMethod
	{
		protected UnannotatedJavaResourceMethod(JavaResourceMethod method){
			super(method);
		}

		public Kind getKind() {
			return Kind.METHOD;
		}

		public String getMethodName() {
			return this.member.getMethodName();
		}

		public boolean isFor(MethodSignature methodSignature, int occurrence) {
			return this.member.isFor(methodSignature, occurrence);
		}

		public ListIterable<String> getParameterTypeNames() {
			return this.member.getParameterTypeNames();
		}

		public int getParametersSize() {
			return 0;
		}

		public boolean isConstructor() {
			return this.member.isConstructor();
		}
	}

}
