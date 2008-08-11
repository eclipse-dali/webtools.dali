/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.elorm.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.common.translators.BooleanTranslator;
import org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmFactory;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class ColumnTranslator extends Translator
	implements EclipseLinkOrmXmlMapper
{		
	private Translator[] children;	
	
	
	public ColumnTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature, END_TAG_NO_INDENT);
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlColumnImpl();
	}

	@Override
	public Translator[] getChildren(Object target, int versionID) {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
	}
		
	protected Translator[] createChildren() {
		return new Translator[] {
			createNameTranslator(),
			createUniqueTranslator(),
			createNullableTranslator(),
			createInsertableTranslator(),
			createUpdatableTranslator(),
			createColumnDefinitionTranslator(),
			createTableTranslator(),
			createLengthTranslator(),
			createPrecisionTranslator(),
			createScaleTranslator(),
		};
	}
	
	private Translator createNameTranslator() {
		return new Translator(NAME, ECLIPSELINK_ORM_PKG.getXmlNamedColumn_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createUniqueTranslator() {
		return new BooleanTranslator(UNIQUE, ECLIPSELINK_ORM_PKG.getXmlAbstractColumn_Unique(), DOM_ATTRIBUTE);
	}
	
	private Translator createNullableTranslator() {
		return new BooleanTranslator(NULLABLE, ECLIPSELINK_ORM_PKG.getXmlAbstractColumn_Nullable(), DOM_ATTRIBUTE);
	}
	
	private Translator createInsertableTranslator() {
		return new BooleanTranslator(INSERTABLE, ECLIPSELINK_ORM_PKG.getXmlAbstractColumn_Insertable(), DOM_ATTRIBUTE);
	}
	
	private Translator createUpdatableTranslator() {
		return new BooleanTranslator(UPDATABLE, ECLIPSELINK_ORM_PKG.getXmlAbstractColumn_Updatable(), DOM_ATTRIBUTE);
	}
	
	private Translator createColumnDefinitionTranslator() {
		return new Translator(COLUMN_DEFINITION, ECLIPSELINK_ORM_PKG.getXmlNamedColumn_ColumnDefinition(), DOM_ATTRIBUTE);
	}
	
	private Translator createTableTranslator() {
		return new Translator(TABLE, ECLIPSELINK_ORM_PKG.getXmlAbstractColumn_Table(), DOM_ATTRIBUTE);
	}
	
	private Translator createLengthTranslator() {
		return new Translator(LENGTH, ECLIPSELINK_ORM_PKG.getXmlColumn_Length(), DOM_ATTRIBUTE);
	}
	
	private Translator createPrecisionTranslator() {
		return new Translator(PRECISION, ECLIPSELINK_ORM_PKG.getXmlColumn_Precision(), DOM_ATTRIBUTE);
	}
	
	private Translator createScaleTranslator() {
		return new Translator(SCALE, ECLIPSELINK_ORM_PKG.getXmlColumn_Scale(), DOM_ATTRIBUTE);
	}
}
