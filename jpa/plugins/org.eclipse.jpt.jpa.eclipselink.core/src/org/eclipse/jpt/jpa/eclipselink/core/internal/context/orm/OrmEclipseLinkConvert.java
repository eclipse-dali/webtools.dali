/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.Association;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.SimpleAssociation;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.ConverterTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.AbstractOrmConverter;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertibleMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedConverter;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkConvert
	extends AbstractOrmConverter
	implements EclipseLinkConvert
{
	protected String specifiedConverterName;
	protected String defaultConverterName;

	protected OrmEclipseLinkConverter<?> converter;


	protected static final OrmEclipseLinkConverter.Adapter[] CONVERTER_ADAPTER_ARRAY = new OrmEclipseLinkConverter.Adapter[] {
		OrmEclipseLinkCustomConverter.Adapter.instance(),
		OrmEclipseLinkTypeConverter.Adapter.instance(),
		OrmEclipseLinkObjectTypeConverter.Adapter.instance(),
		OrmEclipseLinkStructConverter.Adapter.instance()
	};
	protected static final Iterable<OrmEclipseLinkConverter.Adapter> CONVERTER_ADAPTERS = new ArrayIterable<OrmEclipseLinkConverter.Adapter>(CONVERTER_ADAPTER_ARRAY);
                                                                                                                      

	public OrmEclipseLinkConvert(OrmAttributeMapping parent, OrmConverter.Owner owner) {
		super(parent, owner);
		this.specifiedConverterName = this.getXmlConvertibleMapping().getConvert();
		this.converter = this.buildConverter();
	}


	// ********** synchronize/update **********

	@Override
	public EclipseLinkPersistenceUnit getPersistenceUnit(){
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedConverterName_(this.getXmlConvertibleMapping().getConvert());
		this.syncConverter();
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultConverterName(this.buildDefaultConverterName());
		if (this.converter != null) {
			this.converter.update();
		}
	}


	// ********** converter name **********

	public String getConverterName() {
		return (this.specifiedConverterName != null) ? this.specifiedConverterName : this.defaultConverterName;
	}

	public String getSpecifiedConverterName() {
		return this.specifiedConverterName;
	}

	public void setSpecifiedConverterName(String name) {
		this.setSpecifiedConverterName_(name);
		this.getXmlConvertibleMapping().setConvert(name);
	}

	protected void setSpecifiedConverterName_(String name) {
		String old = this.specifiedConverterName;
		this.specifiedConverterName = name;
		this.firePropertyChanged(SPECIFIED_CONVERTER_NAME_PROPERTY, old, name);
	}

	public String getDefaultConverterName() {
		return this.defaultConverterName;
	}

	protected void setDefaultConverterName(String name) {
		String old = this.defaultConverterName;
		this.defaultConverterName = name;
		this.firePropertyChanged(DEFAULT_CONVERTER_NAME_PROPERTY, old, name);
	}

	protected String buildDefaultConverterName() {
		return DEFAULT_CONVERTER_NAME;
	}


	// ********** converter **********

	public OrmEclipseLinkConverter<?> getConverter() {
		return this.converter;
	}

	public void setConverter(Class<? extends EclipseLinkConverter> converterType) {
		if (converterType == null) {
			if (this.converter != null) {
				this.setConverter_(null);
				this.removeXmlConverters();
			}
		} else {
			if ((this.converter == null) || (this.converter.getType() != converterType)) {
				// note: we may also remove the XML converter we want;
				// but if we leave it, the resulting sync will screw things up...
				this.removeXmlConverters();
				OrmEclipseLinkConverter.Adapter converterAdapter = this.getConverterAdapter(converterType);
				this.setConverter_(converterAdapter.buildConverter(this));
				converterAdapter.addXmlConverter(this.getXmlConvertibleMapping(), this.converter.getXmlConverter());
			}
		}
	}

	protected void setConverter_(OrmEclipseLinkConverter<?> converter) {
		OrmEclipseLinkConverter<?> old = this.converter;
		this.converter = converter;
		this.firePropertyChanged(CONVERTER_PROPERTY, old, converter);
	}

	protected void removeXmlConverters() {
		XmlConvertibleMapping xmlMapping = this.getXmlConvertibleMapping();
		for (OrmEclipseLinkConverter.Adapter adapter : this.getConverterAdapters()) {
			adapter.removeXmlConverter(xmlMapping);
		}
	}

	protected OrmEclipseLinkConverter<?> buildConverter() {
		XmlConvertibleMapping xmlMapping = this.getXmlConvertibleMapping();
		for (OrmEclipseLinkConverter.Adapter adapter : this.getConverterAdapters()) {
			OrmEclipseLinkConverter<?> ormConverter = adapter.buildConverter(xmlMapping, this);
			if (ormConverter != null) {
				return ormConverter;
			}
		}
		return null;
	}

	protected void syncConverter() {
		Association<OrmEclipseLinkConverter.Adapter, XmlNamedConverter> assoc = this.getXmlConverter();
		if (assoc == null) {
			if (this.converter != null) {
				this.setConverter_(null);
			}
		} else {
			OrmEclipseLinkConverter.Adapter adapter = assoc.getKey();
			XmlNamedConverter xmlConverter = assoc.getValue();
			if ((this.converter != null) &&
					(this.converter.getType() == adapter.getConverterType()) &&
					(this.converter.getXmlConverter() == xmlConverter)) {
				this.converter.synchronizeWithResourceModel();
			} else {
				this.setConverter_(adapter.buildConverter(xmlConverter, this));
			}
		}
	}

	/**
	 * Return the first XML converter we find along with its corresponding
	 * adapter. Return <code>null</code> if there are no converters in the XML.
	 */
	protected Association<OrmEclipseLinkConverter.Adapter, XmlNamedConverter> getXmlConverter() {
		XmlConvertibleMapping xmlMapping = this.getXmlConvertibleMapping();
		for (OrmEclipseLinkConverter.Adapter adapter : this.getConverterAdapters()) {
			XmlNamedConverter xmlConverter = adapter.getXmlConverter(xmlMapping);
			if (xmlConverter != null) {
				return new SimpleAssociation<OrmEclipseLinkConverter.Adapter, XmlNamedConverter>(adapter, xmlConverter);
			}
		}
		return null;
	}


	// ********** converter adapters **********

	/**
	 * Return the converter adapter for the specified converter type.
	 */
	protected OrmEclipseLinkConverter.Adapter getConverterAdapter(Class<? extends EclipseLinkConverter> converterType) {
		for (OrmEclipseLinkConverter.Adapter adapter : this.getConverterAdapters()) {
			if (adapter.getConverterType() == converterType) {
				return adapter;
			}
		}
		throw new IllegalArgumentException("unknown converter type: " + converterType.getName()); //$NON-NLS-1$
	}

	protected Iterable<OrmEclipseLinkConverter.Adapter> getConverterAdapters() {
		return CONVERTER_ADAPTERS;
	}


	// ********** misc **********

	protected XmlConvertibleMapping getXmlConvertibleMapping() {
		// cast to EclipseLink type
		return (XmlConvertibleMapping) super.getXmlAttributeMapping();
	}

	public Class<? extends Converter> getType() {
		return EclipseLinkConvert.class;
	}

	public void initialize() {
		this.specifiedConverterName = ""; //$NON-NLS-1$
		this.getXmlConvertibleMapping().setConvert(this.specifiedConverterName);
	}


	// ********** refactoring **********

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return (this.converter != null) ?
				this.converter.createRenameTypeEdits(originalType, newName) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return (this.converter != null) ?
				this.converter.createRenamePackageEdits(originalPackage, newName) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return (this.converter != null) ?
				this.converter.createMoveTypeEdits(originalType, newPackage) :
				EmptyIterable.<ReplaceEdit>instance();
	}


	// ********** validation **********

	/**
	 * The converters are validated in the persistence unit.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit#validateConverters(List, IReporter)
	 */
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// converters are validated in the persistence unit
		this.validateConverterName(messages);
	}
	
	private void validateConverterName(List<IMessage> messages) {
		String converterName = this.getConverterName();
		if (converterName == null) {
			return;
		}

		if (CollectionTools.contains(this.getPersistenceUnit().getUniqueConverterNames(), converterName)) {
			return;
		}

		if (ArrayTools.contains(RESERVED_CONVERTER_NAMES, converterName)) {
			return;
		}

		messages.add(
			DefaultEclipseLinkJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				EclipseLinkJpaValidationMessages.ID_MAPPING_UNRESOLVED_CONVERTER_NAME,
				new String[] {
					converterName,
					this.getParent().getName()
				},
				this.getParent(),
				this.getValidationTextRange()
			)
		);	
	}
	
	@Override
	protected TextRange getXmlValidationTextRange() {
		return this.getXmlConvertibleMapping().getConvertTextRange();
	}


	// ********** adapter **********

	public static class Adapter
		implements OrmConverter.Adapter, OrmConverter.Owner
	{
		private static final Adapter INSTANCE = new Adapter();
		public static Adapter instance() {
			return INSTANCE;
		}

		private Adapter() {
			super();
		}

		public Class<? extends Converter> getConverterType() {
			return EclipseLinkConvert.class;
		}

		public OrmConverter buildConverter(OrmAttributeMapping parent, OrmXmlContextNodeFactory factory) {
			XmlConvertibleMapping xmlMapping = (XmlConvertibleMapping) parent.getXmlAttributeMapping();
			return (xmlMapping.getConvert() == null) ? null : new OrmEclipseLinkConvert(parent, this);
		}

		public boolean isActive(XmlAttributeMapping xmlMapping) {
			return ((XmlConvertibleMapping) xmlMapping).getConvert() != null;
		}

		public OrmConverter buildNewConverter(OrmAttributeMapping parent, OrmXmlContextNodeFactory factory) {
			return new OrmEclipseLinkConvert(parent, this);
		}

		public void clearXmlValue(XmlAttributeMapping xmlMapping) {
			((XmlConvertibleMapping) xmlMapping).setConvert(null);
		}

		public JptValidator buildValidator(Converter converter, ConverterTextRangeResolver textRangeResolver) {
			return JptValidator.Null.instance();
		}
	}
}
