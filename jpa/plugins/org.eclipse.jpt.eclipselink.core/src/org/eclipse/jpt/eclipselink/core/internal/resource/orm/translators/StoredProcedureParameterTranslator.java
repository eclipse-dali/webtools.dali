/*******************************************************************************
 *  Copyright (c) 2009 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class StoredProcedureParameterTranslator extends Translator 
	implements EclipseLinkOrmXmlMapper
{
	private Translator[] children;	
	
	
	public StoredProcedureParameterTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature, END_TAG_NO_INDENT);
	}
	
	@Override
	protected Translator[] getChildren() {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
	}
	
	protected Translator[] createChildren() {
		return new Translator[] {
			createDirectionTranslator(),
			createNameTranslator(),
			createQueryParameterTranslator(),
			createTypeTranslator(),
			createJdbcTypeTranslator(),
			createJdbcTypeNameTranslator(),
		};
	}

	private Translator createDirectionTranslator() {
		return new Translator(PARAMETER__DIRECTION, ECLIPSELINK_ORM_PKG.getXmlStoredProcedureParameter_Direction(), DOM_ATTRIBUTE);
	}

	private Translator createNameTranslator() {
		return new Translator(PARAMETER__NAME, ECLIPSELINK_ORM_PKG.getXmlStoredProcedureParameter_Name(), DOM_ATTRIBUTE);
	}

	private Translator createQueryParameterTranslator() {
		return new Translator(PARAMETER__QUERY_PARAMETER, ECLIPSELINK_ORM_PKG.getXmlStoredProcedureParameter_QueryParameter(), DOM_ATTRIBUTE);
	}

	private Translator createTypeTranslator() {
		return new Translator(PARAMETER__TYPE, ECLIPSELINK_ORM_PKG.getXmlStoredProcedureParameter_Type(), DOM_ATTRIBUTE);
	}

	private Translator createJdbcTypeTranslator() {
		return new Translator(PARAMETER__JDBC_TYPE, ECLIPSELINK_ORM_PKG.getXmlStoredProcedureParameter_JdbcType(), DOM_ATTRIBUTE);
	}

	private Translator createJdbcTypeNameTranslator() {
		return new Translator(PARAMETER__JDBC_TYPE_NAME, ECLIPSELINK_ORM_PKG.getXmlStoredProcedureParameter_JdbcTypeName(), DOM_ATTRIBUTE);
	}
}
