/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.OrmElementCollectionTemporalConverterValidator;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.OrmMapKeyTemporalConverterValidator;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.OrmTemporalConverterValidator;
import org.eclipse.jpt.jpa.core.resource.orm.TemporalType;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.core.resource.orm.XmlConvertibleMapping;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlMapKeyConvertibleMapping_2_0;

/**
 * <code>orm.xml</code> temporal/map key temporal converter
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmBaseTemporalConverter
	extends BaseTemporalConverter, OrmConverter
{
	// ********** parent adapter **********

	public interface ParentAdapter 
		extends OrmConverter.ParentAdapter
	{
		TemporalType getXmlTemporalType();

		void setXmlTemporalType(TemporalType temporalType);

		TextRange getTemporalTextRange();
	}


	// ********** adapter **********

	abstract static class AbstractAdapter
		implements OrmConverter.Adapter
	{
		AbstractAdapter() {
			super();
		}

		public Class<BaseTemporalConverter> getConverterType() {
			return BaseTemporalConverter.class;
		}

		public OrmConverter buildConverter(OrmAttributeMapping parent, OrmXmlContextModelFactory factory) {
			XmlConvertibleMapping xmlMapping = (XmlConvertibleMapping) parent.getXmlAttributeMapping();
			return (xmlMapping.getTemporal() == null) ? null : factory.buildOrmBaseTemporalConverter(this.buildParentAdapter(parent, xmlMapping));
		}

		protected abstract OrmBaseTemporalConverter.ParentAdapter buildParentAdapter(OrmAttributeMapping parent, XmlConvertibleMapping mapping); 


		public boolean isActive(XmlAttributeMapping xmlMapping) {
			return ((XmlConvertibleMapping) xmlMapping).getTemporal() != null;
		}

		public OrmConverter buildNewConverter(OrmAttributeMapping parent, OrmXmlContextModelFactory factory) {
			return factory.buildOrmBaseTemporalConverter(this.buildParentAdapter(parent, (XmlConvertibleMapping) parent.getXmlAttributeMapping()));
		}

		public void clearXmlValue(XmlAttributeMapping xmlMapping) {
			((XmlConvertibleMapping) xmlMapping).setTemporal(null);
		}

		public abstract static class ConverterParentAdapter
			implements OrmBaseTemporalConverter.ParentAdapter
		{
			private final OrmAttributeMapping parent;
			private final XmlConvertibleMapping mapping;
			public ConverterParentAdapter(OrmAttributeMapping parent, XmlConvertibleMapping mapping) {
				super();
				this.parent = parent;
				this.mapping = mapping;
			}
			public OrmAttributeMapping getConverterParent() {
				return this.parent;
			}
			public void setXmlTemporalType(TemporalType temporalType) {
				this.mapping.setTemporal(temporalType);
			}
			public TemporalType getXmlTemporalType() {
				return this.mapping.getTemporal();
			}
			public TextRange getTemporalTextRange() {
				return this.mapping.getTemporalTextRange();
			}
		}
	}

	public static class BasicAdapter
		extends AbstractAdapter
	{
		private static final Adapter INSTANCE = new BasicAdapter();
		public static Adapter instance() {
			return INSTANCE;
		}
	
		private BasicAdapter() {
			super();
		}

		@Override
		protected OrmBaseTemporalConverter.ParentAdapter buildParentAdapter(OrmAttributeMapping parent, XmlConvertibleMapping mapping) {
			return new ConverterParentAdapter(parent, mapping);
		}

		public static class ConverterParentAdapter
			extends AbstractAdapter.ConverterParentAdapter
		{
			public ConverterParentAdapter(OrmAttributeMapping parent, XmlConvertibleMapping mapping) {
				super(parent, mapping);
			}
			public JpaValidator buildValidator(Converter converter) {
				return new OrmTemporalConverterValidator((BaseTemporalConverter) converter);
			}
		}
	}

	public static class ElementCollectionAdapter
		extends AbstractAdapter
	{
		private static final Adapter INSTANCE = new ElementCollectionAdapter();
		public static Adapter instance() {
			return INSTANCE;
		}
	
		private ElementCollectionAdapter() {
			super();
		}

		@Override
		protected OrmBaseTemporalConverter.ParentAdapter buildParentAdapter(OrmAttributeMapping parent, XmlConvertibleMapping mapping) {
			return new ConverterParentAdapter(parent, mapping);
		}

		public static class ConverterParentAdapter
			extends AbstractAdapter.ConverterParentAdapter
		{
			public ConverterParentAdapter(OrmAttributeMapping parent, XmlConvertibleMapping mapping) {
				super(parent, mapping);
			}
			public JpaValidator buildValidator(Converter converter) {
				return new OrmElementCollectionTemporalConverterValidator((BaseTemporalConverter) converter);
			}
		}
	}


	// ********** map key temporal adapter **********

	public static class MapKeyAdapter
		implements OrmConverter.Adapter
	{
		private static final MapKeyAdapter INSTANCE = new MapKeyAdapter();
		public static MapKeyAdapter instance() {
			return INSTANCE;
		}

		private MapKeyAdapter() {
			super();
		}

		public Class<BaseTemporalConverter> getConverterType() {
			return BaseTemporalConverter.class;
		}

		public OrmConverter buildConverter(OrmAttributeMapping parent, OrmXmlContextModelFactory factory) {
			XmlMapKeyConvertibleMapping_2_0 xmlMapping = (XmlMapKeyConvertibleMapping_2_0) parent.getXmlAttributeMapping();
			return (xmlMapping.getMapKeyTemporal() == null) ? null : factory.buildOrmBaseTemporalConverter(this.buildParentAdapter(parent, xmlMapping));
		}

		protected OrmBaseTemporalConverter.ParentAdapter buildParentAdapter(OrmAttributeMapping parent, XmlMapKeyConvertibleMapping_2_0 mapping) {
			return new ConverterParentAdapter(parent, mapping);
		}

		public static class ConverterParentAdapter
			implements OrmBaseTemporalConverter.ParentAdapter
		{
			private final OrmAttributeMapping parent;
			private final XmlMapKeyConvertibleMapping_2_0 mapping;
			public ConverterParentAdapter(OrmAttributeMapping parent, XmlMapKeyConvertibleMapping_2_0 mapping) {
				super();
				this.parent = parent;
				this.mapping = mapping;
			}
			public OrmAttributeMapping getConverterParent() {
				return this.parent;
			}
			public void setXmlTemporalType(TemporalType temporalType) {
				this.mapping.setMapKeyTemporal(temporalType);
			}
			public TemporalType getXmlTemporalType() {
				return this.mapping.getMapKeyTemporal();
			}
			public TextRange getTemporalTextRange() {
				return this.mapping.getMapKeyTemporalTextRange();
			}
			public JpaValidator buildValidator(Converter converter) {
				return new OrmMapKeyTemporalConverterValidator((BaseTemporalConverter) converter);
			}
		}

		public boolean isActive(XmlAttributeMapping xmlMapping) {
			return ((XmlMapKeyConvertibleMapping_2_0) xmlMapping).getMapKeyTemporal() != null;
		}

		public OrmConverter buildNewConverter(OrmAttributeMapping parent, OrmXmlContextModelFactory factory) {
			return factory.buildOrmBaseTemporalConverter(this.buildParentAdapter(parent, (XmlMapKeyConvertibleMapping_2_0) parent.getXmlAttributeMapping()));
		}

		public void clearXmlValue(XmlAttributeMapping xmlMapping) {
			((XmlMapKeyConvertibleMapping_2_0) xmlMapping).setMapKeyTemporal(null);
		}
	}
}
