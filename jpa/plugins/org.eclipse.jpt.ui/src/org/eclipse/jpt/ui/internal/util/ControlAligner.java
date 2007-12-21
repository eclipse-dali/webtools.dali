/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.util;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;

/**
 * This class is responsible to set a preferred width on the registered widgets
 * (either <code>Control</code> or <code>ControlAligner</code>) based on the
 * widest widget.
 * <p>
 * Important: The layout data has to be a <code>GridData</code>. If none is set,
 * then a new <code>GridData</code> is automatically created.
 * <p>
 * Here an example of the result if this aligner is used to align controls
 * within either one or two group boxes, the controls added are the labels in
 * this case.
 * <pre>
 * -Group Box 1------------------------------
 * |                     ------------------ |
 * | Name:               | I              | |
 * |                     ------------------ |
 * |                     ---------          |
 * | Preallocation Size: |     |I|          |
 * |                     ---------          |
 * |                     ------------------ |
 * | Descriptor:         |              |v| |
 * |                     ------------------ |
 * ------------------------------------------
 * -Group Box 2------------------------------
 * |                     ------------------ |
 * | Mapping Type:       |              |V| |
 * |                     ------------------ |
 * |                     ------------------ |
 * | Check in Script:    |I               | |
 * |                     ------------------ |
 * ------------------------------------------</pre>
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public final class ControlAligner
{
	/**
	 * <code>true</code> if the length of every control needs to be updated
	 * when control are added or removed; <code>false</code> to add or remove
	 * the control and then at the end invoke {@link #revalidateSize()}.
	 */
	private boolean autoValidate;

	/**
	 * The utility class used to support bound properties.
	 */
	private Collection<ControlListener> changeSupport;

	/**
	 * The listener added to each of the controls that listens only to a text
	 * change.
	 */
	private ControlListener controlListener;

	/**
	 * Prevents infinite recursion when recalculating the preferred width.
	 * This happens in an hierarchy of <code>ControlAligner</code>s. The lock
	 * has to be placed here and not in the {@link ControlAlignerWrapper}.
	 */
	private boolean locked;

	/**
	 * The length of the widest control. If the length was not calculated, then
	 * this value is -1.
	 */
	private int maximumWidth;

	/**
	 * The collection of {@link Wrapper}s encapsulating either <code>Control</code>s
	 * or {@link ControlAligner}s.
	 */
	private Collection<Wrapper> wrappers;

	/**
	 * A null-<code>Point</code> object used to clear the preferred size.
	 */
	private static final Point DEFAULT_SIZE = new Point(SWT.DEFAULT, SWT.DEFAULT);

	/**
	 * Creates a new <code>ControlAligner</code>.
	 */
	public ControlAligner()
	{
		super();
		initialize();
	}

	/**
	 * Creates a new <code>ControlAligner</code>.
	 *
	 * @param items The collection of <code>Component</code>s
	 */
	public ControlAligner(Collection<? extends Control> components)
	{
		this();
		addAllComponents(components);
	}

	/**
	 * Adds the given control. Its preferred width will be used along with the
	 * width of all the other controls in order to get the widest control and
	 * use its width as the width for all the controls.
	 *
	 * @param control The control to be added
	 */
	public void add(Control control)
	{
		Wrapper wrapper = buildWrapper(control);
		wrapper.addControlListener(controlListener);
		wrappers.add(wrapper);

		revalidate();
	}

	/**
	 * Adds the given control. Its preferred width will be used along with the
	 * width of all the other controls in order to get the widest control and
	 * use its width as the width for all the controls.
	 *
	 * @param controlAligner The <code>ControlAligner</code> to be added
	 * @exception IllegalArgumentException Can't add the ControlAligner to itself
	 */
	public void add(ControlAligner controlAligner)
	{
		if (controlAligner == this)
		{
			throw new IllegalArgumentException("Can't add the ControlAligner to itself");
		}

		Wrapper wrapper = buildWrapper(controlAligner);
		wrapper.addControlListener(controlListener);
		wrappers.add(wrapper);

		if (!controlAligner.wrappers.isEmpty())
		{
			revalidate();
		}
	}

	/**
	 * Adds the items contained in the given collection into this
	 * <code>ControlAligner</code>. The preferred width of each item will be
	 * used along with the width of all the other items in order to get the
	 * widest control and use its width as the width for all the controls.
	 *
	 * @param items The collection of <code>Control</code>s
	 */
	public void addAll(Collection<? extends Control> items)
	{
		// Deactivate the auto validation while adding all the Controls
		// in order to improve performance
		boolean oldAutoValidate = autoValidate;
		autoValidate = false;

		for (Control item : items)
		{
			add(item);
		}

		autoValidate = oldAutoValidate;
		revalidate();
	}

	/**
	 * Adds the items contained in the given collection into this
	 * <code>ControlAligner</code>. The preferred width of each item will be
	 * used along with the width of all the other items in order to get the
	 * widest component and use its width as the width for all the components.
	 *
	 * @param items The collection of <code>ControlAligner</code>s
	 */
	public void addAllComponentAligners(Collection<ControlAligner> aligners)
	{
		// Deactivate the auto validation while adding all the JComponents and/or
		// ComponentAligners in order to improve performance
		boolean oldAutoValidate = autoValidate;
		autoValidate = false;

		for (ControlAligner aligner : aligners)
		{
			add(aligner);
		}

		autoValidate = oldAutoValidate;
		revalidate();
	}

	/**
	 * Adds the items contained in the given collection into this
	 * <code>ControlAligner</code>. The preferred width of each item will be
	 * used along with the width of all the other items in order to get the
	 * widest component and use its width as the width for all the components.
	 *
	 * @param items The collection of <code>Control</code>s
	 */
	public void addAllComponents(Collection<? extends Control> components)
	{
		// Deactivate the auto validation while adding all the JComponents and/or
		// ComponentAligners in order to improve performance
		boolean oldAutoValidate = autoValidate;
		autoValidate = false;

		for (Control component : components)
		{
			add(component);
		}

		autoValidate = oldAutoValidate;
		revalidate();
	}

   /**
	 * Adds the given <code>ControListener</code>.
	 *
	 * @param listener The <code>ControlListener</code> to be added
	 */
	private void addControlListener(ControlListener listener)
	{
		if (changeSupport == null)
		{
		    changeSupport = new ArrayList<ControlListener>();
		}

		changeSupport.add(listener);
   }

   /**
	 * Creates a new <code>Wrapper</code> that encapsulates the given source.
	 *
	 * @param control The control to be wrapped
	 * @return A new {@link Wrapper}
	 */
	private Wrapper buildWrapper(Control control)
	{
		return new ControlWrapper(control);
	}

	/**
	 * Creates a new <code>Wrapper</code> that encapsulates the given source.
	 *
	 * @param ControlAligner The <code>ControlAligner</code> to be wrapped
	 * @return A new {@link ControlAlignerWrapper}
	 */
	private Wrapper buildWrapper(ControlAligner ControlAligner)
	{
		return new ControlAlignerWrapper(ControlAligner);
	}

   /**
	 * Reports a bound property change.
	 *
	 * @param oldValue the old value of the property (as an int)
	 * @param newValue the new value of the property (as an int)
	 */
	private void controlResized(int oldValue, int newValue)
	{
		if ((changeSupport != null) && (oldValue != newValue))
		{
			// Set a dummy widget otherwise EventObject will thrown a NPE for its source
			Event event  = new Event();
			event.widget = SWTUtil.getShell();

			ControlEvent controlEvent = new ControlEvent(event);

			// It seems we need to use reflection so the source can properly be set
			ClassTools.setFieldValue(controlEvent, "source", this);

			for (ControlListener listener : changeSupport)
			{
				listener.controlResized(controlEvent);
			}
		}
	}

	/**
	 * Returns the length of the widest control. If the length was not
	 * calculated, then this value is -1.
	 *
	 * @return The width of the widest control or -1 if the length has not
	 * been calculated yet
	 */
	public int getMaximumWidth()
	{
		return maximumWidth;
	}

	/**
	 * Returns the size by determining which control has the greatest
	 * width.
	 *
	 * @return The size of this <code>ControlAligner</code>, which is
	 * {@link #getMaximumWidth()} for the width
	 */
	private Point getPreferredSize()
	{
		if (maximumWidth == -1)
		{
			recalculateWidth();
		}

		return new Point(maximumWidth, 0);
	}

	/**
	 * Initializes this <code>ControlAligner</code>.
	 */
	private void initialize()
	{
		this.autoValidate    = true;
		this.maximumWidth    = -1;
		this.controlListener = new ControlHandler();
		this.wrappers        = new ArrayList<Wrapper>();
	}

	/**
	 * Invalidates the size of the given object.
	 *
	 * @param source The source object to be invalidated
	 */
	private void invalidate(Object source)
	{
		Wrapper wrapper = retrieveWrapper(source);

		if (wrapper.isLocked())
		{
			return;
		}

		Point size = wrapper.getCachedSize();
		size.x = 0;
		size.y = 0;

		wrapper.setPreferredSize(DEFAULT_SIZE);
	}

	/**
	 * Determines whether the length of each control should be set each time a
	 * control is added or removed. If the control's text is changed and
	 * {@link #isAutoValidate()} returns <code>true</code> then the length of
	 * each control is automatically updated. When <code>false</code> is returned,
	 * {@link #revalidateSize()}has to be called manually.
	 *
	 * @return <code>true</code> to recalculate the length of every control
	 * when a control is either added or removed; <code>false</code> to allow
	 * all the controls to be either added or removed before invoking
	 * {@link #revalidateSize()}
	 */
	public boolean isAutoValidate()
	{
		return autoValidate;
	}

	/**
	 * Determines whether the wrapped component is visible or not, which will
	 * determine if its preferred width will be included in the calculation of
	 * this <code>ComponentAligner</code>'s minimum width.
	 *
	 * @return <code>true</code> if the source is visible; <code>false</code>
	 * otherwise
	 */
	private boolean isVisible()
	{
		boolean visible = true;

		for (Wrapper wrapper : wrappers)
		{
			visible &= wrapper.isVisible();
		}

		return visible;
	}

	/**
	 * Updates the maximum length based on the widest control. This methods
	 * does not update the width of the controls.
	 */
	private void recalculateWidth()
	{
		int width = -1;

		for (Wrapper wrapper : wrappers)
		{
			Point size = wrapper.getCachedSize();

			// The size has not been calculated yet
			if ((size.y == 0) && wrapper.isVisible())
			{
				Point newSize = wrapper.getPreferredSize();

				size.x = newSize.x;
				size.y = newSize.y;
			}

			// Only keep the greatest width
			width = Math.max(size.x, width);
		}

		locked = true;
		setMaximumWidth(width);
		locked = false;
	}

	/**
	 * Removes the given control. Its preferred width will not be used when
	 * calculating the widest control.
	 *
	 * @param control The control to be removed
	 */
	public void remove(Control control)
	{
		Wrapper wrapper = retrieveWrapper(control);
		wrapper.removeControlListener(controlListener);
		wrappers.remove(wrapper);

//		if (control.isPreferredSizeSet())
//		{
//			control.setPreferredSize(null);
//		}

		revalidate();
	}

	/**
	 * Removes the given <code>ControlAligner</code>. Its preferred width
	 * will not be used when calculating the widest control.
	 *
	 * @param controlAligner The <code>ControlAligner</code> to be removed
	 */
	public void remove(ControlAligner controlAligner)
	{
		Wrapper wrapper = retrieveWrapper(controlAligner);
		wrapper.removeControlListener(controlListener);
		wrappers.remove(wrapper);

		revalidate();
	}

	/**
	 * Removes the given <code>ControlListener</code>.
	 *
	 * @param listener The <code>ControlListener</code> to be removed
	 */
	private void removeControlListener(ControlListener listener)
	{
		changeSupport.remove(listener);

		if (changeSupport.isEmpty())
		{
			changeSupport = null;
		}
	}

	/**
	 * Retrieves the <code>Wrapper</code> that is encapsulating the given object.
	 *
	 * @param source Either a <code>Control</code> or a <code>ControlAligner</code>
	 * @return Its <code>Wrapper</code>
	 */
	private Wrapper retrieveWrapper(Object source)
	{
		for (Wrapper wrapper : wrappers)
		{
			if (wrapper.getSource() == source)
			{
				return wrapper;
			}
		}

		throw new IllegalArgumentException("Can't retrieve the Wrapper for " + source);
	}

	/**
	 * If the count of control is greater than one and {@link #isAutoValidate()}
	 * returns <code>true</code>, then the size of all the registered
	 * <code>Control</code>s will be udpated.
	 */
	private void revalidate()
	{
		if (autoValidate)
		{
			recalculateWidth();
			revalidatePreferredSizeImp();
		}
	}

	/**
	 * Updates the preferred size of every component based on the widest
	 * component.
	 */
	private void revalidatePreferredSizeImp()
	{
		for (Wrapper wrapper : wrappers)
		{
			Point size = wrapper.getCachedSize();
			size = new Point(maximumWidth, size.y);
			wrapper.setPreferredSize(size);
		}
	}

	/**
	 * Updates the size of every control based on the widest control.
	 */
	public void revalidateSize()
	{
		recalculateWidth();
		revalidateSizeImp();
	}

	/**
	 * Updates the size of every control based on the widest control.
	 */
	private void revalidateSizeImp()
	{
		// Set the preferred width for every control
		for (Wrapper wrapper : wrappers)
		{
			Point size = wrapper.getCachedSize();
			size = new Point(maximumWidth, size.y);
			wrapper.setPreferredSize(size);
		}
	}

	/**
	 * Sets the length of the widest control. If the length was not calulcated,
	 * then this value is -1.
	 *
	 * @param maximumWidth The width of the widest control
	 */
	private void setMaximumWidth(int maximumWidth)
	{
		int oldMaximumWidth = getMaximumWidth();
		this.maximumWidth = maximumWidth;
		controlResized(oldMaximumWidth, maximumWidth);
	}

	/**
	 * Returns a string representation of this <code>ControlAligner</code>.
	 *
	 * @return Information about this object
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		StringTools.buildToStringFor(this, sb);
		sb.append("autoValidate=");
		sb.append(autoValidate);
		sb.append(", maximumWidth=");
		sb.append(maximumWidth);
		sb.append(", wrappers=");
		sb.append(wrappers);
		return sb.toString();
	}

	/**
	 * This <code>Wrapper</code> encapsulates a {@link ControlAligner}.
	 */
	private class ControlAlignerWrapper implements Wrapper
	{
		/**
		 * The cached size, which is {@link ControlAligner#maximumWidth}.
		 */
		private Point cachedSize;

		/**
		 * The <code>ControlAligner</code> encapsulated by this
		 * <code>Wrapper</code>.
		 */
		private final ControlAligner controlAligner;

		/**
		 * Creates a new <code>ControlAlignerWrapper</code> that encapsulates
		 * the given <code>ControlAligner</code>.
		 *
		 * @param controlAligner The <code>ControlAligner</code> to be
		 * encapsulated by this <code>Wrapper</code>
		 */
		private ControlAlignerWrapper(ControlAligner controlAligner)
		{
			super();

			this.controlAligner = controlAligner;
			cachedSize = new Point(controlAligner.maximumWidth, 0);
		}

		/*
		 * (non-Javadoc)
		 */
		public void addControlListener(ControlListener listener)
		{
			controlAligner.addControlListener(listener);
		}

		/*
		 * (non-Javadoc)
		 */
		public Point getCachedSize()
		{
			return cachedSize;
		}

		/*
		 * (non-Javadoc)
		 */
		public Point getPreferredSize()
		{
			return controlAligner.getPreferredSize();
		}

		/*
		 * (non-Javadoc)
		 */
		public Object getSource()
		{
			return controlAligner;
		}

		/*
		 * (non-Javadoc)
		 */
		public boolean isLocked()
		{
			return controlAligner.locked;
		}

		/*
		 * (non-Javadoc)
		 */
		public boolean isVisible()
		{
			return controlAligner.isVisible();
		}

		/*
		 * (non-Javadoc)
		 */
		public void removeControlListener(ControlListener listener)
		{
			controlAligner.removeControlListener(listener);
		}

		/*
		 * (non-Javadoc)
		 */
		public void setPreferredSize(Point size)
		{
			if (size == DEFAULT_SIZE)
			{
				controlAligner.maximumWidth = -1;
			}
			else if (controlAligner.maximumWidth != size.x)
			{
				controlAligner.maximumWidth = size.x;
				controlAligner.revalidateSizeImp();
			}
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		public String toString()
		{
			StringBuffer sb = new StringBuffer();
			StringTools.buildToStringFor(this, sb);
			sb.append("cachedSize=");
			sb.append(cachedSize);
			sb.append(", controlAligner=");
			sb.append(controlAligner);
			return sb.toString();
		}
	}

	/**
	 * The listener added to each of the control that listens only to a text
	 * change.
	 */
	private class ControlHandler implements ControlListener
	{
		/*
		 * (non-Javadoc)
		 */
		public void controlMoved(ControlEvent e)
		{
			// Nothing to do
		}

		/*
		 * (non-Javadoc)
		 */
		public void controlResized(ControlEvent e)
		{
			invalidate(e.getSource());
			revalidate();
		}
	}

	/**
	 * This <code>Wrapper</code> encapsulates a {@link Control}.
	 */
	private class ControlWrapper implements Wrapper
	{
		/**
		 * The cached size, which is control's size.
		 */
		private Point cachedSize;

		/**
		 * The control to be encapsulated by this <code>Wrapper</code>.
		 */
		private final Control control;

		/**
		 * Creates a new <code>controlWrapper</code> that encapsulates the given
		 * control.
		 *
		 * @param control The control to be encapsulated by this <code>Wrapper</code>
		 */
		private ControlWrapper(Control control)
		{
			super();

			this.control = control;
			cachedSize = new Point(0, 0);
		}

		/*
		 * (non-Javadoc)
		 */
		public void addControlListener(ControlListener listener)
		{
			control.addControlListener(listener);
		}

		/*
		 * (non-Javadoc)
		 */
		public Point getCachedSize()
		{
			return cachedSize;
		}

		/*
		 * (non-Javadoc)
		 */
		public Point getPreferredSize()
		{
			return control.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		}

		/*
		 * (non-Javadoc)
		 */
		public Object getSource()
		{
			return control;
		}

		/*
		 * (non-Javadoc)
		 */
		public boolean isLocked()
		{
			return false;
		}

		/*
		 * (non-Javadoc)
		 */
		public boolean isVisible()
		{
			return control.isVisible();
		}

		/*
		 * (non-Javadoc)
		 */
		public void removeControlListener(ControlListener listener)
		{
			control.removeControlListener(listener);
		}

		/*
		 * (non-Javadoc)
		 */
		public void setPreferredSize(Point size)
		{
			GridData data = (GridData) control.getLayoutData();

			if (data == null)
			{
				data = new GridData();
				data.horizontalAlignment = SWT.FILL;
				control.setLayoutData(data);
			}

			data.widthHint  = size.x;
			data.heightHint = size.y;
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		public String toString()
		{
			StringBuffer sb = new StringBuffer();
			StringTools.buildToStringFor(this, sb);
			sb.append("cachedSize=");
			sb.append(cachedSize);
			sb.append(", control=");
			sb.append(control);
			return sb.toString();
		}
	}

	/**
	 * This <code>Wrapper</code> helps to encapsulate heterogeneous objects and
	 * apply the same behavior on them.
	 */
	private interface Wrapper
	{
	   /**
		 * Adds a <code>IPropertyChangeListener</code> for a specific property.
		 * The listener will be invoked only when a call on
		 * <code>firePropertyChange</code> names that specific property.
		 *
		 * @param listener The <code>ControlListener</code> to be added
		 */
		public void addControlListener(ControlListener listener);

		/**
		 * Returns the cached size of the encapsulated source.
		 *
		 * @return A non-<code>null</code> size
		 */
		public Point getCachedSize();

		/**
		 * Returns the preferred size of the wrapped source.
		 *
		 * @return The preferred size
		 */
		public Point getPreferredSize();

		/**
		 * Returns the encapsulated object.
		 *
		 * @return The object that is been wrapped
		 */
		public Object getSource();

		/**
		 * Prevents infinite recursion when recalculating the preferred width.
		 * This happens in an hierarchy of <code>ControlAligner</code>s.
		 *
		 * @return <code>true</code> to prevent this <code>Wrapper</code> from
		 * being invalidated; otherwise <code>false</code>
		 */
		public boolean isLocked();

		/**
		 * Determines whether the wrapped component is visible or not, which will
		 * determine if its preferred width will be included in the calculation of
		 * this <code>ComponentAligner</code>'s minimum width.
		 *
		 * @return <code>true</code> if the source is visible; <code>false</code>
		 * otherwise
		 */
		boolean isVisible();

		/**
		 * Removes the given <code>ControlListener</code>.
		 *
		 * @param listener The <code>ControlListener</code> to be removed
		 */
		public void removeControlListener(ControlListener listener);

		/**
		 * Sets the size on the encapsulated source.
		 *
		 * @param size The new size
		 */
		public void setPreferredSize(Point size);
	}
}
