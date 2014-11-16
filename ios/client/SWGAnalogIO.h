#import <Foundation/Foundation.h>
#import "SWGObject.h"
#import "SWGDate.h"


@interface SWGAnalogIO : SWGObject

@property(nonatomic) NSString* name;  

@property(nonatomic) NSNumber* position;  

@property(nonatomic) NSNumber* value;  

@property(nonatomic) SWGDate* timestamp;  

- (id) name: (NSString*) name
     position: (NSNumber*) position
     value: (NSNumber*) value
     timestamp: (SWGDate*) timestamp;

- (id) initWithValues: (NSDictionary*)dict;
- (NSDictionary*) asDictionary;


@end

