/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.util;

import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.mm.algorithms.styles.AdaptedGradientColoredAreas;
import org.eclipse.graphiti.mm.algorithms.styles.GradientColoredArea;
import org.eclipse.graphiti.mm.algorithms.styles.GradientColoredAreas;
import org.eclipse.graphiti.mm.algorithms.styles.LocationType;
import org.eclipse.graphiti.mm.algorithms.styles.StylesFactory;
import org.eclipse.graphiti.util.IGradientType;
import org.eclipse.graphiti.util.IPredefinedRenderingStyle;
import org.eclipse.graphiti.util.PredefinedColoredAreas;

public class JPAEditorPredefinedColoredAreas extends PredefinedColoredAreas 
											 implements IJPAEditorPredefinedRenderingStyle {

	/**
	 * The color-areas, which are used for default elements with the ID
	 * {@link #GREEN_WHITE_GLOSS_ID}.
	 */
	private static GradientColoredAreas getGreenWhiteGlossDefaultAreas() {
		final GradientColoredAreas gradientColoredAreas = StylesFactory.eINSTANCE.createGradientColoredAreas();
		final EList<GradientColoredArea> gcas = gradientColoredAreas.getGradientColor();

		addGradientColoredArea(gcas, "F8FEFB", 0, LocationType.LOCATION_TYPE_ABSOLUTE_START, "F8FEFB", 1, //$NON-NLS-1$ //$NON-NLS-2$
				LocationType.LOCATION_TYPE_ABSOLUTE_START);
		addGradientColoredArea(gcas, "EDFCF5", 1, LocationType.LOCATION_TYPE_ABSOLUTE_START, "EDFCF5", 2, //$NON-NLS-1$ //$NON-NLS-2$
				LocationType.LOCATION_TYPE_ABSOLUTE_START);
		addGradientColoredArea(gcas, "DEFAED", 2, LocationType.LOCATION_TYPE_ABSOLUTE_START, "DEFAED", 3, //$NON-NLS-1$ //$NON-NLS-2$
				LocationType.LOCATION_TYPE_ABSOLUTE_START);
		addGradientColoredArea(gcas, "D4F8E7", 3, LocationType.LOCATION_TYPE_ABSOLUTE_START, "FAFCFB", 2, //$NON-NLS-1$ //$NON-NLS-2$
				LocationType.LOCATION_TYPE_ABSOLUTE_END);
		addGradientColoredArea(gcas, "E2E9E5", 2, LocationType.LOCATION_TYPE_ABSOLUTE_END, "E2E9E5", 0, //$NON-NLS-1$ //$NON-NLS-2$
				LocationType.LOCATION_TYPE_ABSOLUTE_END);
		gradientColoredAreas.setStyleAdaption(IPredefinedRenderingStyle.STYLE_ADAPTATION_DEFAULT);
		return gradientColoredAreas;
	}

	/**
	 * The color-areas, which are used for primary selected elements with the ID
	 * {@link #GREEN_WHITE_GLOSS_ID}.
	 */
	private static GradientColoredAreas getGreenWhiteGlossPrimarySelectedAreas() {
		final GradientColoredAreas gradientColoredAreas = StylesFactory.eINSTANCE.createGradientColoredAreas();
		gradientColoredAreas.setStyleAdaption(IPredefinedRenderingStyle.STYLE_ADAPTATION_PRIMARY_SELECTED);
		final EList<GradientColoredArea> gcas = gradientColoredAreas.getGradientColor();

		addGradientColoredArea(gcas, "EEFDF6", 0, LocationType.LOCATION_TYPE_ABSOLUTE_START, "EEFDF6", 1, //$NON-NLS-1$ //$NON-NLS-2$
				LocationType.LOCATION_TYPE_ABSOLUTE_START);
		addGradientColoredArea(gcas, "D0F9E6", 1, LocationType.LOCATION_TYPE_ABSOLUTE_START, "D0F9E6", 2, //$NON-NLS-1$ //$NON-NLS-2$
				LocationType.LOCATION_TYPE_ABSOLUTE_START);
		addGradientColoredArea(gcas, "ACF4D2", 2, LocationType.LOCATION_TYPE_ABSOLUTE_START, "ACF4D2", 3, //$NON-NLS-1$ //$NON-NLS-2$
				LocationType.LOCATION_TYPE_ABSOLUTE_START);
		addGradientColoredArea(gcas, "81EAB9", 3, LocationType.LOCATION_TYPE_ABSOLUTE_START, "AAF2D0", 2, //$NON-NLS-1$ //$NON-NLS-2$
				LocationType.LOCATION_TYPE_ABSOLUTE_END);
		addGradientColoredArea(gcas, "9AE0BF", 2, LocationType.LOCATION_TYPE_ABSOLUTE_END, "9AE0BF", 0, //$NON-NLS-1$ //$NON-NLS-2$
				LocationType.LOCATION_TYPE_ABSOLUTE_END);
		return gradientColoredAreas;
	}

	/**
	 * The color-areas, which are used for secondary selected elements with the
	 * ID {@link #GREEN_WHITE_GLOSS_ID}.
	 */
	private static GradientColoredAreas getGreenWhiteGlossSecondarySelectedAreas() {
		final GradientColoredAreas gradientColoredAreas = StylesFactory.eINSTANCE.createGradientColoredAreas();
		gradientColoredAreas.setStyleAdaption(IPredefinedRenderingStyle.STYLE_ADAPTATION_SECONDARY_SELECTED);
		final EList<GradientColoredArea> gcas = gradientColoredAreas.getGradientColor();

		addGradientColoredArea(gcas, "F5FEF9", 0, LocationType.LOCATION_TYPE_ABSOLUTE_START, "F5FEF9", 1, //$NON-NLS-1$ //$NON-NLS-2$
				LocationType.LOCATION_TYPE_ABSOLUTE_START);
		addGradientColoredArea(gcas, "E2FCEF", 1, LocationType.LOCATION_TYPE_ABSOLUTE_START, "E2FCEF", 2, //$NON-NLS-1$ //$NON-NLS-2$
				LocationType.LOCATION_TYPE_ABSOLUTE_START);
		addGradientColoredArea(gcas, "CBF9E3", 2, LocationType.LOCATION_TYPE_ABSOLUTE_START, "CBF9E3", 3, //$NON-NLS-1$ //$NON-NLS-2$
				LocationType.LOCATION_TYPE_ABSOLUTE_START);
		addGradientColoredArea(gcas, "BBF7DA", 3, LocationType.LOCATION_TYPE_ABSOLUTE_START, "C5F7E0", 2, //$NON-NLS-1$ //$NON-NLS-2$
				LocationType.LOCATION_TYPE_ABSOLUTE_END);
		addGradientColoredArea(gcas, "B2E5CD", 2, LocationType.LOCATION_TYPE_ABSOLUTE_END, "B2E5CD", 0, //$NON-NLS-1$ //$NON-NLS-2$
				LocationType.LOCATION_TYPE_ABSOLUTE_END);
		return gradientColoredAreas;
	}

	/**
	 * The color-areas, which are used for elements where an action is allowed
	 * with the ID {@link #GREEN_WHITE_GLOSS_ID}.
	 */
	private static GradientColoredAreas getGreeenWhiteGlossActionAllowedAreas() {
		final GradientColoredAreas gradientColoredAreas = StylesFactory.eINSTANCE.createGradientColoredAreas();
		gradientColoredAreas.setStyleAdaption(IPredefinedRenderingStyle.STYLE_ADAPTATION_ACTION_ALLOWED);
		final EList<GradientColoredArea> gcas = gradientColoredAreas.getGradientColor();

		addGradientColoredArea(gcas, "9900CC", 0, LocationType.LOCATION_TYPE_ABSOLUTE_START, "336699", 0, //$NON-NLS-1$ //$NON-NLS-2$
				LocationType.LOCATION_TYPE_ABSOLUTE_END);
		return gradientColoredAreas;
	}

	/**
	 * The color-areas, which are used for elements where an action is forbidden
	 * with the ID {@link #GREEN_WHITE_GLOSS_ID}.
	 */
	private static GradientColoredAreas getGreenWhiteGlossActionForbiddenAreas() {
		final GradientColoredAreas gradientColoredAreas = StylesFactory.eINSTANCE.createGradientColoredAreas();
		gradientColoredAreas.setStyleAdaption(IPredefinedRenderingStyle.STYLE_ADAPTATION_ACTION_FORBIDDEN);
		final EList<GradientColoredArea> gcas = gradientColoredAreas.getGradientColor();

		addGradientColoredArea(gcas, "FF00CC", 0, LocationType.LOCATION_TYPE_ABSOLUTE_START, "FF0066", 0, //$NON-NLS-1$ //$NON-NLS-2$
				LocationType.LOCATION_TYPE_ABSOLUTE_END);
		return gradientColoredAreas;
	}
	
	
	public static AdaptedGradientColoredAreas getGreenWhiteGlossAdaptions() {
		final AdaptedGradientColoredAreas agca = StylesFactory.eINSTANCE.createAdaptedGradientColoredAreas();
		agca.setDefinedStyleId(GREEN_WHITE_GLOSS_ID);
		agca.setGradientType(IGradientType.VERTICAL);
		agca.getAdaptedGradientColoredAreas().add(IPredefinedRenderingStyle.STYLE_ADAPTATION_DEFAULT, getGreenWhiteGlossDefaultAreas());
		agca.getAdaptedGradientColoredAreas().add(IPredefinedRenderingStyle.STYLE_ADAPTATION_PRIMARY_SELECTED,
				getGreenWhiteGlossPrimarySelectedAreas());
		agca.getAdaptedGradientColoredAreas().add(IPredefinedRenderingStyle.STYLE_ADAPTATION_SECONDARY_SELECTED,
				getGreenWhiteGlossSecondarySelectedAreas());
		agca.getAdaptedGradientColoredAreas().add(IPredefinedRenderingStyle.STYLE_ADAPTATION_ACTION_ALLOWED,
				getGreeenWhiteGlossActionAllowedAreas());
		agca.getAdaptedGradientColoredAreas().add(IPredefinedRenderingStyle.STYLE_ADAPTATION_ACTION_FORBIDDEN,
				getGreenWhiteGlossActionForbiddenAreas());
		return agca;
	}	

	public static AdaptedGradientColoredAreas getAdaptedGradientColoredAreas(String id) {
		AdaptedGradientColoredAreas res = PredefinedColoredAreas.getAdaptedGradientColoredAreas(id);
		if (res != null)
			return res;
		if (GREEN_WHITE_GLOSS_ID.equals(id))
			return getGreenWhiteGlossAdaptions();
		return null;
	}
}
