package com.dialltay.ehelper.adapter.in.requestDTO;


public record SliceCursor(int page, int size) {
    public static SliceCursor moveTo(int page, int size) {
        return new SliceCursor(page, size);
    }
}