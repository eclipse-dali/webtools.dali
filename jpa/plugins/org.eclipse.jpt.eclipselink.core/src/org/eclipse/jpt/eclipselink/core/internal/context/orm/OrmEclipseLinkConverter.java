/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedConverter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class OrmEclipseLinkConverter<X extends XmlNamedConverter>
	extends AbstractOrmXmlContextNode
	implements EclipseLinkConverter
{
	protected final X xmlConverter;

	protected String name;


	protected OrmEclipseLinkConverter(XmlContextNode parent, X xmlConverter) {
		super(parent);
		this.xmlConverter = xmlConverter;
		this.name = xmlConverter.getName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName_(this.xmlConverter.getName());
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
		this.setName_(name);
		this.xmlConverter.setName(name);
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}


	// ********** misc **********

	public X getXmlConverter() {
		return this.xmlConverter;
	}

	@Override
	public EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}

	protected EntityMappings getEntityMappings() {
		return (EntityMappings) this.getMappingFileRoot();
	}

	public char getEnclosingTypeSeparator() {
		return '$';
	}


	// ********** refactoring **********

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return EmptyIterable.instance();
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return EmptyIterable.instance();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return EmptyIterable.instance();
	}

	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateConverterNames(messages);
	}

	protected void validateConverterNames(List<IMessage> messages){
			String name = this.getName();
			if (StringTools.stringIsEmpty(name)) {
				messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY, 
							EclipseLinkJpaValidationMessages.CONVERTER_NAME_UNDEFINED, 
							new String[] {},
							this,
							this.getNameTextRange()
					));
			} else {
				List<String> reportedNames = new ArrayList<String>();
				for (ListIterator<EclipseLinkConverter> globalConverters = this.getPersistenceUnit().allConverters(); globalConverters.hasNext(); ) {
					if ( this.duplicates(globalConverters.next()) && !reportedNames.contains(name)	){
						messages.add(
							DefaultEclipseLinkJpaValidationMessages.buildMessage(
									IMessage.HIGH_SEVERITY,
									EclipseLinkJpaValidationMessages.CONVERTER_DUPLICATE_NAME,
									new String[] {name},
									this,
									this.getNameTextRange()
							)
						);
						reportedNames.add(name);
					}
				}
			}
		}
	
	public boolean overrides(EclipseLinkConverter converter) {
		if (this.name == null) {
			return false;
		}
		// this isn't ideal, but it will have to do until we have further adopter input
		return this.name.equals(converter.getName()) &&
				(converter instanceof JavaEclipseLinkConverter);
	}

	public boolean duplicates(EclipseLinkConverter converter) {
		return (this != converter) &&
				! StringTools.stringIsEmpty(this.name) &&
				this.name.equals(converter.getName()) &&
				! this.overrides(converter) &&
				! converter.overrides(this);
	}

	public TextRange getValidationTextRange() {
		return this.xmlConverter.getValidationTextRange();
	}

	public TextRange getNameTextRange() {
		return this.xmlConverter.getNameTextRange();
	}

	// ********** adapter **********

	/**
	 * This interface allows a client to interact with various
	 * EclipseLink <code>orm.xml</code> converters via the same protocol.
	 */
	public interface Adapter
	{
		/**
		 * Return the type of converter handled by the adapter.
		 */
		Class<? extends EclipseLinkConverter> getConverterType();

		/**
		 * Build a converter corresponding to the specified XML converter
		 * container if the container holds the adapter's XML converter. Return
		 * <code>null</code> otherwise.
		 * This is used to build a converter during construction of the
		 * converter's parent.
		 */
		OrmEclipseLinkConverter<? extends XmlNamedConverter> buildConverter(XmlConverterHolder xmlConverterContainer, XmlContextNode parent);

		/**
		 * Return the adapter's XML converter for the specified XML converter
		 * container. Return <code>null</code> if the container does not hold
		 * the adapter's XML converter.
		 * The returned XML converter is compared to the parent's
		 * converter's XML converter while the context model is synchronized
		 * with the resource model. If it has changed, the parent will build
		 * a new converter (via the adapter).
		 * 
		 * @see #buildConverter(XmlNamedConverter, XmlContextNode)
		 */
		XmlNamedConverter getXmlConverter(XmlConverterHolder xmlConverterContainer);

		/**
		 * Build a converter using the specified XML converter.
		 * This is used when the context model is synchronized with the
		 * resource model (and the resource model has changed).
		 * 
		 * @see #getXmlConverter(XmlConverterHolder)
		 */
		OrmEclipseLinkConverter<? extends XmlNamedConverter> buildConverter(XmlNamedConverter xmlConverter, XmlContextNode parent);

		/**
		 * Build a new converter and its corresponding XML converter.
		 * The XML converter will be added to the XML converter container once
		 * the context converter has been added to the context model.
		 * 
		 * @see #addXmlConverter(XmlConverterHolder, XmlNamedConverter)
		 */
		OrmEclipseLinkConverter<? extends XmlNamedConverter> buildConverter(XmlContextNode parent);

		/**
		 * Remove the adapter's XML converter from the specified XML converter
		 * container.
		 */
		void removeXmlConverter(XmlConverterHolder xmlConverterContainer);

		/**
		 * Add the specified XML converter to the specified XML converter
		 * container.
		 */
		void addXmlConverter(XmlConverterHolder xmlConverterContainer, XmlNamedConverter xmlConverter);
	}

	public abstract static class AbstractAdapter
		implements Adapter
	{
		public OrmEclipseLinkConverter<? extends XmlNamedConverter> buildConverter(XmlConverterHolder xmlConverterContainer, XmlContextNode parent) {
			XmlNamedConverter xmlConverter = this.getXmlConverter(xmlConverterContainer);
			return (xmlConverter == null) ? null : this.buildConverter(xmlConverter, parent);
		}

		public OrmEclipseLinkConverter<? extends XmlNamedConverter> buildConverter(XmlContextNode parent) {
			return this.buildConverter(this.buildXmlConverter(), parent);
		}

		protected abstract XmlNamedConverter buildXmlConverter();

		public void removeXmlConverter(XmlConverterHolder xmlConverterContainer) {
			this.setXmlConverter(xmlConverterContainer, null);
		}

		public void addXmlConverter(XmlConverterHolder xmlConverterContainer, XmlNamedConverter xmlConverter) {
			this.setXmlConverter(xmlConverterContainer, xmlConverter);
		}

		protected abstract void setXmlConverter(XmlConverterHolder xmlConverterContainer, XmlNamedConverter xmlConverter);
	}
}
