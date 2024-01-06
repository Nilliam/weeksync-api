package io.weeksync.weeksync.presentation.dto;

import lombok.Builder;

@Builder
public record AccountInfo (String name, String username) {}
