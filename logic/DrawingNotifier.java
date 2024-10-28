package logic;

import java.util.ArrayList;

/**
 * DrawingNotifier
 * Уведомляет слушателей при необходимости перерисоваться
 */
public abstract class DrawingNotifier {
    protected ArrayList<DrawingListener> listeners;

    public DrawingNotifier() {
        listeners = new ArrayList<DrawingListener>(0);
    }
    
    public void notifyListeners() {
		for (DrawingListener listener : listeners) {
			listener.draw();
		}
	}

    public void addListener(DrawingListener listener) {
        listeners.add(listener);
    }

    public void removeListener(DrawingListener listener) {
        listeners.remove(listener);
    }

    public void removeAllListeners() {
        listeners.clear();
    }
}