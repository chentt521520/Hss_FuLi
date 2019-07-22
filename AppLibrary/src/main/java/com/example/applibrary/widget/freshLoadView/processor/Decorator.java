package com.example.applibrary.widget.freshLoadView.processor;


import com.example.applibrary.widget.freshLoadView.RefreshLayout;

/**
 * Created by lcodecore on 2017/3/3.
 */

public abstract class Decorator implements IDecorator {
    protected IDecorator decorator;
    protected RefreshLayout.CoContext cp;

    public Decorator(RefreshLayout.CoContext processor, IDecorator decorator1) {
        cp = processor;
        decorator = decorator1;
    }
}
