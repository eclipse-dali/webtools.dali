/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ClassName;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkNamedConverterAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class JavaEclipseLinkConverter<A extends EclipseLinkNamedConverterAnnotation>
	extends AbstractJavaJpaContextNode
	implements EclipseLinkConverter
{
	protected final A converterAnnotation;

	protected String name;


	protected JavaEclipseLinkConverter(JavaJpaContextNode parent, A converterAnnotation) {
		super(parent);
		this.converterAnnotation = converterAnnotation;
		this.name = converterAnnotation.getName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName_(this.converterAnnotation.getName());
	}

	@Override
	public void update() {
		super.update();
		this.getPersistenceUnit().addConverter(this);
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.converterAnnotation.setName(name);
		this.setName_(name);
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}


	// ********** misc **********

	public A getConverterAnnotation() {
		return this.converterAnnotation;
	}

	@Override
	public EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}

	public char getEnclosingTypeSeparator() {
		return '.';
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** validation **********

	public boolean overrides(EclipseLinkConverter converter) {
		// java is at the base of the tree
		return false;
	}

	public boolean duplicates(EclipseLinkConverter converter) {
		return (this != converter) &&
				! StringTools.stringIsEmpty(this.name) &&
				this.name.equals(converter.getName()) &&
				! this.overrides(converter) &&
				! converter.overrides(this);
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.converterAnnotation.getTextRange(astRoot);
	}


	// ********** adapter **********

	/**
	 * This interface allows a client to interact with various
	 * EclipseLink Java converters via the same protocol.
	 */
	public interface Adapter
	{
		/**
		 * Return the type of converter handled by the adapter.
		 */
		Class<? extends EclipseLinkConverter> getConverterType();

		/**
		 * Build a converter corresponding to the specified Java resource
		 * persistent member if the member is modified by the adapter's
		 * converter annotation. Return <code>null</code> otherwise.
		 * This is used to build a converter during construction of the
		 * converter's parent.
		 */
		JavaEclipseLinkConverter<? extends EclipseLinkNamedConverterAnnotation> buildConverter(JavaResourcePersistentMember javaResourcePersistentMember, JavaJpaContextNode parent);

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
		 * @see #buildConverter(EclipseLinkNamedConverterAnnotation, JavaJpaContextNode)
		 */
		EclipseLinkNamedConverterAnnotation getConverterAnnotation(JavaResourcePersistentMember javaResourcePersistentMember);

		/**
		 * Build a converter using the specified converter annotation.
		 * This is used when the context model is synchronized with the
		 * resource model (and the resource model has changed).
		 * 
		 * @see #getConverterAnnotation(JavaResourcePersistentMember)
		 */
		JavaEclipseLinkConverter<? extends EclipseLinkNamedConverterAnnotation> buildConverter(EclipseLinkNamedConverterAnnotation converterAnnotation, JavaJpaContextNode parent);

		/**
		 * Build a new converter and, if necessary, its corresponding converter
		 * annotation.
		 */
		JavaEclipseLinkConverter<? extends EclipseLinkNamedConverterAnnotation> buildNewConverter(JavaResourcePersistentMember javaResourcePersistentMember, JavaJpaContextNode parent);

		/**
		 * Remove the adapter's converter annotation from the specified
		 * Java resource persistent member.
		 */
		void removeConverterAnnotation(JavaResourcePersistentMember javaResourcePersistentMember);
	}


	// ********** abstract adapter **********

	public abstract static class AbstractAdapter
		implements JavaEclipseLinkConverter.Adapter
	{
		public JavaEclipseLinkConverter<? extends EclipseLinkNamedConverterAnnotation> buildConverter(JavaResourcePersistentMember member, JavaJpaContextNode parent) {
			EclipseLinkNamedConverterAnnotation annotation = this.getConverterAnnotation(member);
			return (annotation == null) ? null : this.buildConverter(annotation, parent);
		}

		public EclipseLinkNamedConverterAnnotation getConverterAnnotation(JavaResourcePersistentMember member) {
			return (EclipseLinkNamedConverterAnnotation) member.getAnnotation(this.getAnnotationName());
		}

		protected abstract String getAnnotationName();

		public JavaEclipseLinkConverter<? extends EclipseLinkNamedConverterAnnotation> buildNewConverter(JavaResourcePersistentMember member, JavaJpaContextNode parent) {
			return this.buildConverter(this.buildConverterAnnotationIfNecessary(member), parent);
		}

		protected EclipseLinkNamedConverterAnnotation buildConverterAnnotationIfNecessary(JavaResourcePersistentMember member) {
			// the annotation may already be present, after we remove the other converter annotations
			EclipseLinkNamedConverterAnnotation annotation = this.getConverterAnnotation(member);
			return (annotation != null) ? annotation : this.buildConverterAnnotation(member);
		}

		protected EclipseLinkNamedConverterAnnotation buildConverterAnnotation(JavaResourcePersistentMember member) {
			return (EclipseLinkNamedConverterAnnotation) member.addAnnotation(this.getAnnotationName());
		}

		public void removeConverterAnnotation(JavaResourcePersistentMember member) {
			member.removeAnnotation(this.getAnnotationName());
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, ClassName.getSimpleName(this.getAnnotationName()));
		}
	}
	
	public TextRange getNameTextRange(CompilationUnit astRoot){
		return getConverterAnnotation().getNameTextRange(astRoot);
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.validateConverterNames(messages, astRoot);
	}

	protected void validateConverterNames(List<IMessage> messages, CompilationUnit astRoot){
		String name = this.getName();
		if (StringTools.stringIsEmpty(name)) {
			messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY, 
							EclipseLinkJpaValidationMessages.CONVERTER_NAME_UNDEFINED, 
							new String[] {},
							this,
							this.getNameTextRange(astRoot)
							));
		} else {
			List<String> reportedNames = new ArrayList<String>();
			for (ListIterator<EclipseLinkConverter> globalConverters = this.getPersistenceUnit	().allConverters(); globalConverters.hasNext(); ) {
				if ( this.duplicates( globalConverters.next())  && !reportedNames.contains(name)){
					messages.add(
							DefaultEclipseLinkJpaValidationMessages.buildMessage(
									IMessage.HIGH_SEVERITY,
									EclipseLinkJpaValidationMessages.CONVERTER_DUPLICATE_NAME,
									new String[] {name},
									this,
									this.getNameTextRange(astRoot)
							));
					reportedNames.add(name);
				}
			}
		}
	}
}
