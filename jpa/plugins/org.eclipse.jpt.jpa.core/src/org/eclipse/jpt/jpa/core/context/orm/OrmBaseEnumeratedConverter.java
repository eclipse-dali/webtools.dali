/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.BaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.NullJpaValidator;
import org.eclipse.jpt.jpa.core.resource.orm.EnumType;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.core.resource.orm.XmlConvertibleMapping;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlMapKeyConvertibleMapping_2_0;

/**
 * <code>orm.xml</code> enumerated/map key enumerated converter
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmBaseEnumeratedConverter
	extends BaseEnumeratedConverter, OrmConverter
{
	// ********** parent adapter **********

	public interface ParentAdapter
		extends OrmConverter.ParentAdapter
	{
		EnumType getXmlEnumType();

		void setXmlEnumType(EnumType enumType);

		TextRange getEnumTextRange();
	}


	// ********** enumerated adapter **********

	public static class BasicAdapter
		implements OrmConverter.Adapter
	{
		private static final BasicAdapter INSTANCE = new BasicAdapter();
		public static BasicAdapter instance() {
			return INSTANCE;
		}

		private BasicAdapter() {
			super();
		}

		public Class<BaseEnumeratedConverter> getConverterType() {
			return BaseEnumeratedConverter.class;
		}

		public OrmConverter buildConverter(OrmAttributeMapping parent, OrmXmlContextModelFactory factory) {
			XmlConvertibleMapping xmlMapping = (XmlConvertibleMapping) parent.getXmlAttributeMapping();
			return (xmlMapping.getEnumerated() == null) ? null : factory.buildOrmBaseEnumeratedConverter(this.buildConverterParentAdapter(parent, xmlMapping));
		}

		protected OrmBaseEnumeratedConverter.ParentAdapter buildConverterParentAdapter(OrmAttributeMapping parent, XmlConvertibleMapping mapping) {
			return new ConverterParentAdapter(parent, mapping);
		}

		public static class ConverterParentAdapter
			implements OrmBaseEnumeratedConverter.ParentAdapter
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
			public void setXmlEnumType(EnumType enumType) {
				this.mapping.setEnumerated(enumType);
			}
			public EnumType getXmlEnumType() {
				return this.mapping.getEnumerated();
			}
			public TextRange getEnumTextRange() {
				return this.mapping.getEnumeratedTextRange();
			}
			public JpaValidator buildValidator(Converter converter) {
				return NullJpaValidator.instance();
			}
		}

		public boolean isActive(XmlAttributeMapping xmlMapping) {
			return ((XmlConvertibleMapping) xmlMapping).getEnumerated() != null;
		}

		public OrmConverter buildNewConverter(OrmAttributeMapping parent, OrmXmlContextModelFactory factory) {
			return factory.buildOrmBaseEnumeratedConverter(this.buildConverterParentAdapter(parent, (XmlConvertibleMapping) parent.getXmlAttributeMapping()));
		}

		public void clearXmlValue(XmlAttributeMapping xmlMapping) {
			((XmlConvertibleMapping) xmlMapping).setEnumerated(null);
		}
	}

	// ********** map key enumerated adapter **********

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

		public Class<BaseEnumeratedConverter> getConverterType() {
			return BaseEnumeratedConverter.class;
		}

		public OrmConverter buildConverter(OrmAttributeMapping parent, OrmXmlContextModelFactory factory) {
			XmlMapKeyConvertibleMapping_2_0 xmlMapping = (XmlMapKeyConvertibleMapping_2_0) parent.getXmlAttributeMapping();
			return (xmlMapping.getMapKeyEnumerated() == null) ? null : factory.buildOrmBaseEnumeratedConverter(this.buildParentAdapter(parent, xmlMapping));
		}

		protected OrmBaseEnumeratedConverter.ParentAdapter buildParentAdapter(OrmAttributeMapping parent, XmlMapKeyConvertibleMapping_2_0 mapping) {
			return new ConverterParentAdapter(parent, mapping);
		}

		public static class ConverterParentAdapter
			implements OrmBaseEnumeratedConverter.ParentAdapter
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
			public void setXmlEnumType(EnumType enumType) {
				this.mapping.setMapKeyEnumerated(enumType);
			}
			public EnumType getXmlEnumType() {
				return this.mapping.getMapKeyEnumerated();
			}
			public TextRange getEnumTextRange() {
				return this.mapping.getMapKeyEnumeratedTextRange();
			}
			public JpaValidator buildValidator(Converter converter) {
				return NullJpaValidator.instance();
			}
		}

		public boolean isActive(XmlAttributeMapping xmlMapping) {
			return ((XmlMapKeyConvertibleMapping_2_0) xmlMapping).getMapKeyEnumerated() != null;
		}

		public OrmConverter buildNewConverter(OrmAttributeMapping parent, OrmXmlContextModelFactory factory) {
			return factory.buildOrmBaseEnumeratedConverter(this.buildParentAdapter(parent, (XmlMapKeyConvertibleMapping_2_0) parent.getXmlAttributeMapping()));
		}

		public void clearXmlValue(XmlAttributeMapping xmlMapping) {
			((XmlMapKeyConvertibleMapping_2_0) xmlMapping).setMapKeyEnumerated(null);
		}
	}
}
