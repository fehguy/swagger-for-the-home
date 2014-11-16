#import <Foundation/Foundation.h>
#import "SWGObject.h"


@interface SWGDeviceState : SWGObject

@property(nonatomic) NSNumber* deviceId;  

@property(nonatomic) NSNumber* state;  

- (id) deviceId: (NSNumber*) deviceId
     state: (NSNumber*) state;

- (id) initWithValues: (NSDictionary*)dict;
- (NSDictionary*) asDictionary;


@end

