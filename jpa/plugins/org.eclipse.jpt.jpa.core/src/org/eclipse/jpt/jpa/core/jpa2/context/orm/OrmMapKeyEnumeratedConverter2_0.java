/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.EnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.ConverterTextRangeResolver;
import org.eclipse.jpt.jpa.core.resource.orm.EnumType;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlMapKeyConvertibleMapping_2_0;

/**
 * <code>orm.xml</code> enumerated converter
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmMapKeyEnumeratedConverter2_0
	extends EnumeratedConverter, OrmConverter
{
	// ********** adapter **********

	public static class Adapter
		implements OrmConverter.Adapter
	{
		private static final Adapter INSTANCE = new Adapter();
		public static Adapter instance() {
			return INSTANCE;
		}

		private Adapter() {
			super();
		}

		public Class<? extends Converter> getConverterType() {
			return EnumeratedConverter.class;
		}

		public OrmConverter buildConverter(OrmAttributeMapping parent, OrmXmlContextNodeFactory factory) {
			XmlMapKeyConvertibleMapping_2_0 xmlMapping = (XmlMapKeyConvertibleMapping_2_0) parent.getXmlAttributeMapping();
			return (xmlMapping.getMapKeyEnumerated() == null) ? null : factory.buildOrmEnumeratedConverter(parent, this.buildOwner(xmlMapping));
		}

		protected OrmEnumeratedConverter.Owner buildOwner(final XmlMapKeyConvertibleMapping_2_0 mapping) {
			return new OrmEnumeratedConverter.Owner() {
				public void setXmlEnumType(EnumType enumType) {
					mapping.setMapKeyEnumerated(enumType);
				}
				
				public EnumType getXmlEnumType() {
					return mapping.getMapKeyEnumerated();
				}
				
				public TextRange getEnumTextRange() {
					return mapping.getMapKeyEnumeratedTextRange();
				}

				public JptValidator buildValidator(Converter converter, ConverterTextRangeResolver textRangeResolver) {
					return JptValidator.Null.instance();
				}
			};
		}

		public boolean isActive(XmlAttributeMapping xmlMapping) {
			return ((XmlMapKeyConvertibleMapping_2_0) xmlMapping).getMapKeyEnumerated() != null;
		}

		public OrmConverter buildNewConverter(OrmAttributeMapping parent, OrmXmlContextNodeFactory factory) {
			return factory.buildOrmEnumeratedConverter(parent, this.buildOwner((XmlMapKeyConvertibleMapping_2_0) parent.getXmlAttributeMapping()));
		}

		public void clearXmlValue(XmlAttributeMapping xmlMapping) {
			((XmlMapKeyConvertibleMapping_2_0) xmlMapping).setMapKeyEnumerated(null);
		}
	}
}
