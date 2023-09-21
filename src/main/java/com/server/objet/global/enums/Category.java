package com.server.objet.global.enums;

public enum Category {
    UX_UI("UX/UI"),
    GRAPHIC_DESIGN("그래픽 디자인"),
    VIDEO_AND_MOTION_GRAPHICS("영상&모션 그래픽"),
    ILLUSTRATION("일러스트레이션"),
    CHARACTER_DESIGN("캐릭터 디자인"),
    PHOTOGRAPHY("사진"),
    CRAFTS("공예"),
    CERAMICS_AND_GLASS("도예/유리");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
