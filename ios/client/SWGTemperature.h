#import <Foundation/Foundation.h>
#import "SWGObject.h"


@interface SWGTemperature : SWGObject

@property(nonatomic) NSNumber* day;  

@property(nonatomic) NSNumber* min;  

@property(nonatomic) NSNumber* max;  

@property(nonatomic) NSNumber* night;  

@property(nonatomic) NSNumber* eve;  

@property(nonatomic) NSNumber* morn;  

- (id) day: (NSNumber*) day
     min: (NSNumber*) min
     max: (NSNumber*) max
     night: (NSNumber*) night
     eve: (NSNumber*) eve
     morn: (NSNumber*) morn;

- (id) initWithValues: (NSDictionary*)dict;
- (NSDictionary*) asDictionary;


@end

