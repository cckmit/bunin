package my.bunin.trade.channel.query;


import lombok.Getter;
import lombok.Setter;
import my.bunin.core.ChannelStatus;
import my.bunin.core.ChannelType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "channel", uniqueConstraints = @UniqueConstraint(columnNames = {"no"}))
public class Channel {

    @Id
    private Long id;

    @Version
    private Long version;

    @Column(length = 64, nullable = false)
    private String no;

    @Enumerated(EnumType.STRING)
    @Column(length = 64, nullable = false)
    private ChannelType type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String desc;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    @Enumerated(EnumType.STRING)
    @Column(length = 64, nullable = false)
    private ChannelStatus status;






}
