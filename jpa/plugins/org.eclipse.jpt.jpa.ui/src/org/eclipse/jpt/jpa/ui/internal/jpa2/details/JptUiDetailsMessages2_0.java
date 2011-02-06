/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali mapping panes.
 *
 * @version 2.3
 * @since 2.3
 */
public class JptUiDetailsMessages2_0 
{
	public static String CascadePane2_0_detach;
	
	public static String CollectionTable2_0Composite_title;
	public static String CollectionTable2_0Composite_joinColumn;
	public static String CollectionTable2_0Composite_name;
	public static String CollectionTable2_0Composite_schema;
	public static String CollectionTable2_0Composite_catalog;
	public static String CollectionTable2_0Composite_overrideDefaultJoinColumns;
	
	public static String DerivedIdentity_title;
	public static String DerivedIdentity_nullDerivedIdentity;
	public static String DerivedIdentity_idDerivedIdentity;
	public static String DerivedIdentity_mapsIdDerivedIdentity;
	public static String DerivedIdentity_mapsIdUnspecifiedValue;
	
	public static String ElementCollectionMapping2_0_label;
	public static String ElementCollectionMapping2_0_linkLabel;
	
	public static String ElementCollectionSection_title;
	public static String AbstractElementCollectionMapping2_0_Composite_valueSectionTitle;
	
	public static String Entity_cacheableLabel;
	public static String Entity_cacheableWithDefaultLabel;
	
	public static String EmbeddedIdMapping2_0MappedByRelationshipPane_label;
	
	public static String IdMapping2_0MappedByRelationshipPane_label;
	
	public static String OrderingComposite_orderColumn;
	
	public static String OrphanRemoval2_0Composite_orphanRemovalLabel;
	public static String OrphanRemoval2_0Composite_orphanRemovalLabelDefault;
	
	public static String LockModeComposite_lockModeLabel;

	public static String LockModeComposite_read;
	public static String LockModeComposite_write;
	public static String LockModeComposite_optimistic;
	public static String LockModeComposite_optimistic_force_increment;
	public static String LockModeComposite_pessimistic_read;
	public static String LockModeComposite_pessimistic_write;
	public static String LockModeComposite_pessimistic_force_increment;
	public static String LockModeComposite_none;

	public static String TargetClassComposite_label;
	
	private static final String BUNDLE_NAME = "jpt_ui_details2_0"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptUiDetailsMessages2_0.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	private JptUiDetailsMessages2_0() {
		throw new UnsupportedOperationException();
	}

}
