/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.utility.internal.ClassNameTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.NullJpaValidator;

/**
 * Java converter
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.1
 */
public interface JavaConverter
	extends Converter
{
	JavaAttributeMapping getParent();

	Annotation getConverterAnnotation();

	/**
	 * Allow the converter to clean up any related annotations before it is
	 * removed itself.
	 */
	void dispose();


	// ********** parent adapter **********

	/**
	 * Interface allowing converters to be used in multiple places
	 * (e.g. basic mappings, collection mappings, etc).
	 */
	public interface ParentAdapter
		extends Converter.ParentAdapter<JavaAttributeMapping>
	{
		// specify generic argument
	}


	// ********** adapter **********

	/**
	 * This interface allows a convertible mapping to interact with various
	 * Java converters via the same protocol.
	 */
	public interface Adapter
	{
		/**
		 * Return the type of converter handled by the adapter.
		 */
		Class<? extends Converter> getConverterType();

		/**
		 * Build a converter corresponding to the specified mapping
		 * if the mapping's resource attribute is modified by the adapter's
		 * converter annotation. Return <code>null</code> otherwise.
		 * This is used to build a converter during construction of the
		 * converter's mapping.
		 */
		JavaConverter buildConverter(JavaAttributeMapping parent, JpaFactory factory);

		/**
		 * Return the adapter's converter annotation for the specified Java
		 * resource persistent member.
		 * Return <code>null</code> if the adapter's converter annotation is
		 * missing. 
		 * The returned converter annotation is compared to the parent's
		 * converter's converter annotation while the context model is synchronized
		 * with the resource model. If it has changed, the parent will build
		 * a new converter (via the adapter).
		 * 
		 * @see #buildConverter(Annotation, JavaAttributeMapping, JpaFactory)
		 */
		Annotation getConverterAnnotation(JavaResourceAttribute javaResourceAttribute);

		/**
		 * Build a converter using the specified converter annotation.
		 * This is used when the context model is synchronized with the
		 * resource model (and the resource model has changed).
		 * 
		 * @see #getConverterAnnotation(JavaResourceAttribute)
		 */
		JavaConverter buildConverter(Annotation converterAnnotation, JavaAttributeMapping parent, JpaFactory factory);

		/**
		 * Build a new converter and, if necessary, its corresponding converter
		 * annotation.
		 */
		JavaConverter buildNewConverter(JavaAttributeMapping parent, JpaFactory factory);

		/**
		 * Remove the adapter's converter annotation from the specified
		 * Java resource persistent member.
		 */
		void removeConverterAnnotation(JavaResourceAttribute javaResourcePersistentAttribute);
	}


	// ********** abstract adapter **********

	public abstract static class AbstractAdapter
		implements JavaConverter.Adapter
	{
		public JavaConverter buildConverter(JavaAttributeMapping parent, JpaFactory factory) {
			Annotation annotation = this.getConverterAnnotation(parent.getResourceAttribute());
			return (annotation == null) ? null : this.buildConverter(annotation, parent, factory);
		}

		protected abstract String getAnnotationName();

		public Annotation getConverterAnnotation(JavaResourceAttribute attribute) {
			return attribute.getAnnotation(this.getAnnotationName());
		}

		public JavaConverter buildNewConverter(JavaAttributeMapping parent, JpaFactory factory) {
			return this.buildConverter(this.buildConverterAnnotationIfNecessary(parent.getResourceAttribute()), parent, factory);
		}

		protected Annotation buildConverterAnnotationIfNecessary(JavaResourceAttribute attribute) {
			// the annotation may already be present, after we remove the other converter annotations
			Annotation annotation = this.getConverterAnnotation(attribute);
			return (annotation != null) ? annotation : this.buildConverterAnnotation(attribute);
		}

		protected Annotation buildConverterAnnotation(JavaResourceAttribute attribute) {
			return attribute.addAnnotation(this.getAnnotationName());
		}

		public void removeConverterAnnotation(JavaResourceAttribute attribute) {
			if (attribute.getAnnotation(this.getAnnotationName()) != null) {
				attribute.removeAnnotation(this.getAnnotationName());
			}
		}

		protected JavaConverter.ParentAdapter buildConverterParentAdapter(JavaAttributeMapping parent) {
			return new ConverterParentAdapter(parent);
		}

		public static class ConverterParentAdapter
			implements JavaConverter.ParentAdapter
		{
			private final JavaAttributeMapping parent;
			public ConverterParentAdapter(JavaAttributeMapping parent) {
				super();
				this.parent = parent;
			}
			public JavaAttributeMapping getConverterParent() {
				return this.parent;
			}
			public JpaValidator buildValidator(Converter converter) {
				return NullJpaValidator.instance();
			}
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, ClassNameTools.simpleName(this.getAnnotationName()));
		}
	}
}
