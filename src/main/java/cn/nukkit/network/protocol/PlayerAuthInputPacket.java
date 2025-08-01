package cn.nukkit.network.protocol;

import cn.nukkit.math.Vector2;
import cn.nukkit.math.Vector2f;
import cn.nukkit.math.Vector3f;
import cn.nukkit.network.protocol.types.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@ToString
@Setter
@Getter
public class PlayerAuthInputPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.PLAYER_AUTH_INPUT_PACKET;

    private float yaw;
    private float pitch;
    private float headYaw;
    private Vector3f position;
    private Vector2 motion;
    private Set<AuthInputAction> inputData = EnumSet.noneOf(AuthInputAction.class);
    private InputMode inputMode;
    private ClientPlayMode playMode;
    private AuthInteractionModel interactionModel;
    /**
     * @deprecated since v748
     */
    private Vector3f vrGazeDirection;
    private long tick;
    private Vector3f delta;
    /**
     * netease only
     */
    private boolean cameraDeparted;
    // private ItemStackRequest itemStackRequest;
    private Map<PlayerActionType, PlayerBlockActionData> blockActionData = new EnumMap<>(PlayerActionType.class);
    /**
     * @since v748
     */
    private Vector2f interactRotation;
    /**
     * @since 575
     */
    private Vector2f analogMoveVector;
    /**
     * @since 649
     */
    private long predictedVehicle;
    /**
     * @since v662 1.20.70
     */
    private Vector2f vehicleRotation;
    /**
     * @since v748
     */
    private Vector3f cameraOrientation;
    /**
     * @since v766
     */
    private Vector2f rawMoveVector;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        this.pitch = this.getLFloat();
        this.yaw = this.getLFloat();
        this.position = this.getVector3f();
        this.motion = new Vector2(this.getLFloat(), this.getLFloat());
        this.headYaw = this.getLFloat();

        long inputData = this.getUnsignedVarLong();
        int inClientPredictedInVehicleOrdinal = AuthInputAction.IN_CLIENT_PREDICTED_IN_VEHICLE.ordinal();
        for (int i = 0; i < AuthInputAction.size(); i++) {
            int offset = 0;
            if (gameVersion.isNetEase() && protocol == ProtocolInfo.v1_21_2 && i >= inClientPredictedInVehicleOrdinal) {
                offset = -1;
            }
            if ((inputData & (1L << i)) != 0) {
                this.inputData.add(AuthInputAction.from(i + offset));
            }
        }

        this.inputMode = InputMode.fromOrdinal((int) this.getUnsignedVarInt());
        this.playMode = ClientPlayMode.fromOrdinal((int) this.getUnsignedVarInt());
        if (this.protocol >= ProtocolInfo.v1_19_0_29) {
            this.interactionModel = AuthInteractionModel.fromOrdinal((int) this.getUnsignedVarInt());
        }

        if (protocol >= ProtocolInfo.v1_21_40) {
            this.interactRotation = this.getVector2f();
        } else {
            if (this.playMode == ClientPlayMode.REALITY) {
                this.vrGazeDirection = this.getVector3f();
            }
        }

        this.tick = this.getUnsignedVarLong();
        this.delta = this.getVector3f();

        if (gameVersion.isNetEase() && protocol >= ProtocolInfo.v1_16_200) {
            this.cameraDeparted = this.getBoolean();
        }

        if (this.inputData.contains(AuthInputAction.PERFORM_ITEM_STACK_REQUEST)) {
            // TODO: this.itemStackRequest = readItemStackRequest(buf, protocolVersion);
            // We are safe to leave this for later, since it is only sent with ServerAuthInventories
        }

        if (this.inputData.contains(AuthInputAction.PERFORM_BLOCK_ACTIONS)) {
            int arraySize = this.getVarInt();
            if (arraySize > 256) {
                throw new IllegalArgumentException("PlayerAuthInputPacket PERFORM_BLOCK_ACTIONS is too long: " + arraySize);
            }
            for (int i = 0; i < arraySize; i++) {
                PlayerActionType type = PlayerActionType.from(this.getVarInt());
                switch (type) {
                    case START_DESTROY_BLOCK:
                    case ABORT_DESTROY_BLOCK:
                    case CRACK_BLOCK:
                    case PREDICT_DESTROY_BLOCK:
                    case CONTINUE_DESTROY_BLOCK:
                        this.blockActionData.put(type, new PlayerBlockActionData(type, this.getSignedBlockPosition(), this.getVarInt()));
                        break;
                    default:
                        this.blockActionData.put(type, new PlayerBlockActionData(type, null, -1));
                }
            }
        }

        if (protocol >= ProtocolInfo.v1_19_70_24) {
            if (protocol >= ProtocolInfo.v1_20_60 && this.inputData.contains(AuthInputAction.IN_CLIENT_PREDICTED_IN_VEHICLE)) {
                if (protocol >= ProtocolInfo.v1_20_70) {
                    this.vehicleRotation = this.getVector2f();
                }
                this.predictedVehicle = this.getVarLong();
            }

            this.analogMoveVector = this.getVector2f();

            if (protocol >= ProtocolInfo.v1_21_40) {
                this.cameraOrientation = this.getVector3f();
            }
            if (protocol >= ProtocolInfo.v1_21_50) {
                this.rawMoveVector = this.getVector2f();
            }
        }
    }

    @Override
    public void encode() {
        // Noop
    }
}