/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.Arrays;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.CompositeIterable;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.AbstractOrmConverter;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.EclipseLinkConvertValidator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertibleMapping;

public class OrmEclipseLinkConvert
	extends AbstractOrmConverter
	implements EclipseLinkConvert
{
	protected String specifiedConverterName;

	public OrmEclipseLinkConvert(OrmAttributeMapping parent, OrmConverter.Owner owner) {
		super(parent, owner);
		this.specifiedConverterName = this.getXmlConvertibleMapping().getConvert();
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
	}

	// ********** converter name **********

	public String getConverterName() {
		return (this.specifiedConverterName != null) ? this.specifiedConverterName : this.getDefaultConverterName();
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
		return DEFAULT_CONVERTER_NAME;
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
		this.specifiedConverterName = DEFAULT_CONVERTER_NAME; //$NON-NLS-1$
		this.getXmlConvertibleMapping().setConvert(this.specifiedConverterName);
	}


	// ********** validation **********
	
	@Override
	protected TextRange getXmlValidationTextRange() {
		return this.getXmlConvertibleMapping().getConvertTextRange();
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.convertValueTouches(pos)) {
			return this.getConverterNames();
		}
		return null;
	}

	protected boolean convertValueTouches(int pos) {
		return this.getXmlConvertibleMapping().convertTouches(pos);
	}

	/**
	 * @return names of the user-defined converters and of reserved converters
	 */
	@SuppressWarnings("unchecked")
	protected Iterable<String> getConverterNames() {
		return new CompositeIterable<String>(
				this.getPersistenceUnit().getUniqueConverterNames(),
				Arrays.asList(EclipseLinkConvert.RESERVED_CONVERTER_NAMES));
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

		public JptValidator buildValidator(Converter converter) {
			return new EclipseLinkConvertValidator((EclipseLinkConvert) converter);
		}
	}
}
