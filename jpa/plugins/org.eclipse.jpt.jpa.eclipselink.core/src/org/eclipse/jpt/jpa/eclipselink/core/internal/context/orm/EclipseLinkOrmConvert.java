/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.Arrays;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.AbstractOrmConverter;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvertibleMapping_2_1;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.EclipseLinkConvertValidator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvert;

public class EclipseLinkOrmConvert
	extends AbstractOrmConverter<OrmConverter.ParentAdapter>
	implements EclipseLinkConvert
{
	protected String specifiedConverterName;

	public EclipseLinkOrmConvert(OrmConverter.ParentAdapter parentAdapter) {
		super(parentAdapter);
		this.specifiedConverterName = this.getXmlConvert() != null ? this.getXmlConvert().getConvert() : null;
	}


	// ********** synchronize/update **********

	@Override
	public EclipseLinkPersistenceUnit getPersistenceUnit(){
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}
	
	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedConverterName_(this.getXmlConvert() != null ? this.getXmlConvert().getConvert() : null);
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
		if (name == null) {
			this.getXmlConvertibleMapping().setConvert(null);
		}
		else {
			this.getXmlConvert().setConvert(name);
		}
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

	protected XmlConvertibleMapping_2_1 getXmlConvertibleMapping() {
		// cast to EclipseLink type
		return (XmlConvertibleMapping_2_1) super.getXmlAttributeMapping();
	}

	protected XmlConvert getXmlConvert() {
		return ((XmlConvert) this.getXmlConvertibleMapping().getConvert());
	}
	
	public Class<EclipseLinkConvert> getConverterType() {
		return EclipseLinkConvert.class;
	}

	public void initialize() {
		this.specifiedConverterName = DEFAULT_CONVERTER_NAME;
		if (this.getXmlConvert() == null) {
			XmlConvert convert = EclipseLinkOrmFactory.eINSTANCE.createXmlConvert();
			convert.setConvert(this.specifiedConverterName);
			this.getXmlConvertibleMapping().setConvert(convert);
		}
		else {
			this.getXmlConvert().setConvert(this.specifiedConverterName);
		}
	}


	// ********** validation **********
	
	@Override
	protected TextRange getXmlValidationTextRange() {
		return this.getXmlConvert() == null ? getXmlConvertibleMapping().getValidationTextRange() : getXmlConvert().getConvertTextRange();
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
		return this.getXmlConvert() == null ? false : this.getXmlConvert().convertTouches(pos);
	}

	/**
	 * @return names of the user-defined converters and of reserved converters
	 */
	@SuppressWarnings("unchecked")
	protected Iterable<String> getConverterNames() {
		return IterableTools.concatenate(
				this.getPersistenceUnit().getUniqueConverterNames(),
				Arrays.asList(EclipseLinkConvert.RESERVED_CONVERTER_NAMES));
	}

	// ********** adapter **********

	public static class Adapter
		implements OrmConverter.Adapter//, OrmConverter.ParentAdapter<OrmAttributeMapping>
	{
		private static final Adapter INSTANCE = new Adapter();
		public static Adapter instance() {
			return INSTANCE;
		}

		private Adapter() {
			super();
		}

		public Class<EclipseLinkConvert> getConverterType() {
			return EclipseLinkConvert.class;
		}

		public OrmConverter buildConverter(OrmAttributeMapping parent, OrmXmlContextModelFactory factory) {
			XmlConvertibleMapping_2_1 xmlMapping = (XmlConvertibleMapping_2_1) parent.getXmlAttributeMapping();
			XmlConvert xmlConvert = (XmlConvert) xmlMapping.getConvert();
			return (xmlConvert == null) ? null : new EclipseLinkOrmConvert(new ConverterParentAdapter(parent));
		}

		public boolean isActive(XmlAttributeMapping xmlMapping) {
			return ((XmlConvertibleMapping_2_1) xmlMapping).getConvert() != null;
		}

		public OrmConverter buildNewConverter(OrmAttributeMapping parent, OrmXmlContextModelFactory factory) {
			return new EclipseLinkOrmConvert(new ConverterParentAdapter(parent));
		}

		public void clearXmlValue(XmlAttributeMapping xmlMapping) {
			((XmlConvertibleMapping_2_1) xmlMapping).setConvert(null);
		}

		public static class ConverterParentAdapter
			implements OrmConverter.ParentAdapter
		{
			private final OrmAttributeMapping parent;
			public ConverterParentAdapter(OrmAttributeMapping parent) {
				super();
				this.parent = parent;
			}
			public OrmAttributeMapping getConverterParent() {
				return this.parent;
			}
			public JpaValidator buildValidator(Converter converter) {
				return new EclipseLinkConvertValidator((EclipseLinkConvert) converter);
			}
		}
	}
}
